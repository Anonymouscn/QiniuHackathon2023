package cn.net.anonymous.workflowcleanserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 工作流 - 清理服务程序
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.workflowcleanserver", "system.*", "api", "util", "config", "dao"})
public class WorkflowCleanServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowCleanServerApplication.class, args);
    }
}