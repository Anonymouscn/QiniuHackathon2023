package cn.net.anonymous.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

/**
 * 腾讯 sms 配置
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sms")
public class TencentSmsConfig {

    /** 用户每天限制接收短信条数 */
    private Integer limitInDay;

    /** 短信发送间隔时间 (min) */
    private Integer interval;

    /** 短信有效期 (min) */
    private Integer efficient;

    /** secretId */
    private String secretId;

    /** secretKey */
    private String secretKey;

    /** appId */
    private String appId;

    /** 短信签名 */
    private String signName;

    /** 模版id */
    private Map<String, String> templateId;

    /** 地域 */
    private String region;

    /** 最少客户端数量 */
    private Integer minClient;

    /** 最多客户端数量 */
    private Integer maxClient;
}