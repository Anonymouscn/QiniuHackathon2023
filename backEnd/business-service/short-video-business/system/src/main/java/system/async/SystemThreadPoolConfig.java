package system.async;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 系统线程池配置
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Configuration
@ConfigurationProperties(prefix = "task.system.pool")
public class SystemThreadPoolConfig
        extends ThreadPoolConfig{
}