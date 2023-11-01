package cn.net.anonymous.filescheduleserver.service.impl;

import api.file.IScheduleService;
import api.file.IWorkflowService;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import pojo.file.dto.FileMeta;
import pojo.file.vo.ServerInfo;
import util.RedisUtil;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 文件调度服务实现
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
@RequiredArgsConstructor
public class ScheduleServiceImpl
        implements IScheduleService {

    private final RedissonClient redissonClient;

    @DubboReference
    private IWorkflowService workflowService;

    /**
     * 申请工作流服务器
     *
     * @param userId 用户id
     * @param meta   文件元数据
     * @return 上传服务器地址信息
     */
    @SneakyThrows
    @Override
    public ServerInfo applyUpload(Long userId, FileMeta meta) {
        if(Strings.isNullOrEmpty(meta.getFilename()))
            throw new IllegalArgumentException("上传文件名不能为空");
        // 存储key为: 用户id + 文件名
        final String key = userId.toString() + "-" + meta.getFilename();
        // 存储value为: 随机生成UUID凭证
        String auth = UUID.randomUUID().toString();
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(auth, Duration.of(15, ChronoUnit.MINUTES));
//        redisUtil.setCacheObject(key, auth, 15, TimeUnit.MINUTES);
        return  workflowService.getServerInfo().setAuth(auth);
    }
}