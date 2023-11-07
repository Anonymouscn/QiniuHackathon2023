package cn.net.anonymous.workflowsliceserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 工作流 - 切片服务程序
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.workflowsliceserver", "system", "api", "util", "config", "dao"})
public class WorkflowSliceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowSliceServerApplication.class, args);
    }
}