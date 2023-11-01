package system.async;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 线程池配置
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class ThreadPoolConfig {

    /** 核心线程数 */
    private int corePoolSize;

    /** 最大线程数 */
    private int maxPoolSize;

    /** 活跃时间 */
    private int keepAliveSeconds;

    /** 队列大小 */
    private int queueCapacity;
}
