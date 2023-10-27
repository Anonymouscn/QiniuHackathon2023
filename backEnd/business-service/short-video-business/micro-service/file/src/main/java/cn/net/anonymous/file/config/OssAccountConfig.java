package cn.net.anonymous.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 对象存储帐号设置
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssAccountConfig {

    private String accessKey;

    private String secretKey;

    private String bucket;
}