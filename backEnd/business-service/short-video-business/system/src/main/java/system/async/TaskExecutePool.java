package system.async;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池配置
 *
 * @author anonymous
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class TaskExecutePool {

    private final SystemThreadPoolConfig systemThreadPoolConfig;

    private final ServiceThreadPoolConfig serviceThreadPoolConfig;

    private final LogThreadPoolConfig logThreadPoolConfig;

    /** 线程池注册表 */
    private final List<ThreadPoolTaskExecutor> executors
            = Collections.synchronizedList(new ArrayList<>());

    /**
     * 装配系统线程池
     *
     * @return 系统线程池
     */
    @Bean("system-executor")
    public Executor getSystemThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(systemThreadPoolConfig.getCorePoolSize());
        executor.setMaxPoolSize(systemThreadPoolConfig.getMaxPoolSize());
        executor.setQueueCapacity(systemThreadPoolConfig.getQueueCapacity());
        executor.setKeepAliveSeconds(systemThreadPoolConfig.getKeepAliveSeconds());
        executor.setThreadNamePrefix("System-Executor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executors.add(executor);
        return executor;
    }

    /**
     * 装配业务线程池
     *
     * @return 业务线程池
     */
    @Bean("service-executor")
    public Executor getServiceThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(serviceThreadPoolConfig.getCorePoolSize());
        executor.setMaxPoolSize(serviceThreadPoolConfig.getMaxPoolSize());
        executor.setQueueCapacity(serviceThreadPoolConfig.getQueueCapacity());
        executor.setKeepAliveSeconds(serviceThreadPoolConfig.getKeepAliveSeconds());
        executor.setThreadNamePrefix("Service-Executor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executors.add(executor);
        return executor;
    }

    /**
     * 装配日志线程池
     *
     * @return 日志线程池
     */
    @Bean("log-executor")
    public Executor getLogThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(logThreadPoolConfig.getCorePoolSize());
        executor.setMaxPoolSize(logThreadPoolConfig.getMaxPoolSize());
        executor.setQueueCapacity(logThreadPoolConfig.getQueueCapacity());
        executor.setKeepAliveSeconds(logThreadPoolConfig.getKeepAliveSeconds());
        executor.setThreadNamePrefix("Log-Executor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executors.add(executor);
        return executor;
    }

    /** 程序结束时关闭所有线程池 */
    @PreDestroy
    public void shutdownExecutors() {
        executors.stream().parallel()
                .forEach(ExecutorConfigurationSupport::shutdown);
    }
}