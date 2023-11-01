package cn.net.anonymous.filescheduleserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 牛牛视界 - 文件调度服务
 *
 * @author anonymous
 * @version 1.0
 */
@EnableConfigurationProperties
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.filescheduleserver", "system.*", "api", "util", "dao"})
public class FileScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileScheduleApplication.class, args);
    }
}