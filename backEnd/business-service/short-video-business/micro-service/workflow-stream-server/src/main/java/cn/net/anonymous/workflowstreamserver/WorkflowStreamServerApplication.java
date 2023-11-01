package cn.net.anonymous.workflowstreamserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 工作流 - 流转换服务程序
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.workflowstreamserver", "system.*", "api", "util", "dao"})
public class WorkflowStreamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowStreamServerApplication.class, args);
    }
}