package cn.net.anonymous.workflowconvertserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 工作流 - 转换服务程序
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.workflowconvertserver", "system.*", "api", "util", "config", "dao"})
public class WorkflowConvertServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowConvertServerApplication.class, args);
    }
}