package cn.net.anonymous.workflowpushserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 工作流 - 推送服务程序
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.workflowpushserver", "system.*", "api", "util", "config", "dao"})
public class WorkflowPushServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowPushServerApplication.class, args);
    }
}