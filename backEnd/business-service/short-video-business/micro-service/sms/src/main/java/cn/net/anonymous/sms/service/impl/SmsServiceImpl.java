package cn.net.anonymous.sms.service.impl;

import api.sms.ISmsService;
import cn.net.anonymous.sms.config.TencentSmsConfig;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import pojo.sms.dto.SmsDto;
import pojo.sms.dto.SmsOperation;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executor;

/**
 * 短信服务接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@Slf4j
@DubboService
@RequiredArgsConstructor
public class SmsServiceImpl
        implements ISmsService {

    /** 认证证书 */
    private Credential credential;

    /** 客户端配置 */
    private ClientProfile clientProfile;

    private final RedissonClient redissonClient;

    @Qualifier("service-executor")
    private final Executor serviceExecutor;

    private final TencentSmsConfig smsConfig;

    /** 租用客户端列表 */
    private List<SmsClient> borrowed;

    /** 归还客户端列表 */
    private List<SmsClient> returned;

    /** 每次扩容客户端增加数量 */
    private final int step = 10;

    /** 当前客户端数量 */
    private int numsOfClient;

    /** 创建客户端缓存池 */
    @PostConstruct
    private void createClientPool() {
        this.borrowed = Collections.synchronizedList(new ArrayList<>());
        this.returned = Collections.synchronizedList(new ArrayList<>());
        this.credential = new Credential(smsConfig.getSecretId(), smsConfig.getSecretKey());
        this.clientProfile = new ClientProfile();
        this.numsOfClient = smsConfig.getMinClient();
        produceClient(numsOfClient);
    }

    /* 生产客户端 */
    private void produceClient(int num) {
        for(int i = 0; i < num; ++i) {
            returned.add(new SmsClient(credential, smsConfig.getRegion(), clientProfile));
        }
    }

    /* 租借客户端 */
    private SmsClient borrowClient() {
        SmsClient client = null;
        if(!returned.isEmpty()) {
            client = returned.remove(0);
            returned.add(client);
        } else {
            int remain = smsConfig.getMinClient() - numsOfClient;
            produceClient(Math.min(remain, step));
            if(!returned.isEmpty()) {
                return borrowClient();
            }
        }
        return client;
    }

    /* 归还客户端 */
    private void returnClient(SmsClient client) {
        returned.add(client);
        borrowed.remove(client);
    }

    /**
     * 发送短信验证码
     *
     * @param phone    手机号
     * @param operation 操作代码
     * @return 是否发送成功
     */
    @SneakyThrows
    @Override
    public String sendSms(String phone, Integer operation) {
        // 获取手机号操作缓存
        RBucket<SmsOperation> operationBucket =
                redissonClient.getBucket("sms-" + phone);
        // 读取操作栈缓存数据
        SmsOperation smsOperation = operationBucket.get();
        // 当天 (24小时) 已发送过短信
        if(smsOperation != null) {
            // 手机号是否被禁止获取短信
            if(smsOperation.getIsBanned()) {
                throw new RuntimeException("手机号已被禁止获取短信");
            }
            // 是否到达当日限制
            if(smsOperation.getOperations().size() >= smsConfig.getLimitInDay()) {
                throw new RuntimeException("短信发送已到达当日限制, 请24小时后重试");
            } else {
                // 获取上次短信发送时间
                List<Map<Long, Integer>> list = smsOperation.getOperations();
                long efficients = list.parallelStream()
                        .map(s -> s.keySet().stream().toList())
                        .filter(s -> !s.isEmpty() &&
                                System.currentTimeMillis() - s.get(0) < 2 * 60 * 1000
                        ).count();
                if(efficients > 0) {
                    throw new RuntimeException("短信仍在有效期内");
                }
                // 更新操作列表
                HashMap<Long, Integer> map = new HashMap<>(1);
                map.put(System.currentTimeMillis(), operation);
                list.add(map);
                smsOperation.setOperations(list);
                // 更新更新时间
                smsOperation.setLatest(System.currentTimeMillis());
                System.out.println("当前操作栈大小 => " + smsOperation.getOperations().size());
            }
            // 当天 (24小时) 未发送过短信
        } else {
            Map<Long, Integer> map = new HashMap<>();
            // 新建操作列表
            List<Map<Long, Integer>> list = new ArrayList<>();
            map.put(System.currentTimeMillis(), operation);
            list.add(map);
            smsOperation = new SmsOperation()
                            .setPhone(phone)
                            .setOperations(list)
                            .setLatest(System.currentTimeMillis())
                            .setIsBanned(false);
        }
        String operationName;
        switch (operation) {
            case 1 -> operationName = "registry";
            case 2 -> operationName = "login";
            case 3 -> operationName = "forget";
            default -> throw new RuntimeException("非法操作");
        }
        // 设置 UUID-key
        String key = "sms-" + operationName + "-" + UUID.randomUUID();
        SmsOperation finalSmsOperation = smsOperation;
//        serviceExecutor.execute(() -> {
            // 生成随机6位验证码
            Random random = new Random(System.currentTimeMillis());
            int code = random.nextInt(899999) + 100000;
            RBucket<String> keyBucket = redissonClient.getBucket(key);
            keyBucket.set(Integer.toString(code),
                    Duration.of(smsConfig.getEfficient(), ChronoUnit.MINUTES));
            String[] args = {Integer.toString(code),
                    smsConfig.getEfficient().toString()};
            // 设置接收手机号
            String[] phones = {phone};
            // 解析腾讯云响应
            SmsClient client = borrowClient();
            SendSmsRequest request = new SendSmsRequest();
            request.setSmsSdkAppId(smsConfig.getAppId());
            request.setSignName(smsConfig.getSignName());
            String templateId = smsConfig.getTemplateId().get(operationName);
            if(Strings.isNullOrEmpty(templateId)) {
                throw new RuntimeException("找不到操作模版");
            }
            request.setTemplateId(templateId);
            request.setTemplateParamSet(args);
            request.setPhoneNumberSet(phones);
            SendSmsResponse response;
            try {
                response = client.SendSms(request);
            } catch (TencentCloudSDKException ex) {
                throw new RuntimeException(ex.getMessage());
            } finally {
                returnClient(client);
            }
            JSONObject originJson = (JSONObject)JSONObject.parse(SendSmsResponse.toJsonString(response));
            String sendStatusArr = originJson.getString("SendStatusSet");
            JSONObject sendStatus = (JSONObject)JSONObject.parse(sendStatusArr.substring(1, sendStatusArr.length() - 1));
            String responseCode = sendStatus.getString("Code");
            String responseMessage = sendStatus.getString("Message");
            log.info("{} sms => To phone [ {} ] response Code [ {} ] and message [ {} ]",
                    operation, phones, responseCode, responseMessage);
            String ok = "Ok";
            if(!ok.equals(responseCode)) {
                throw new RuntimeException("获取短信失败 " + responseMessage);
            }
            operationBucket.set(finalSmsOperation, Duration.of(24, ChronoUnit.HOURS));
//        });
        return key;
    }

    /**
     * 校验短信验证码
     *
     * @param smsDto 短信 dto
     * @return 短信验证码是否正确
     */
    @Override
    public Boolean checkSms(SmsDto smsDto) {
        String auth = smsDto.getAuth();
        RBucket<String> authBucket =
                redissonClient.getBucket(auth);
        String code = authBucket.get();
        if(code == null) throw new RuntimeException("验证码不存在或已过期");
        boolean res = code.equals(smsDto.getCode());
        if(res) authBucket.delete();
        return res;
    }
}