package cn.net.anonymous.fileworkflowserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 牛牛世界 - 工作流服务
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.fileworkflowserver", "system.*", "api", "util","config", "dao"})
public class WorkflowUploadServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowUploadServerApplication.class, args);
    }
}