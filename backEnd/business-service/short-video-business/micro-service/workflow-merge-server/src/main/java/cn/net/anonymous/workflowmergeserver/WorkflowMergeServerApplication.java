package cn.net.anonymous.workflowmergeserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 工作流 - 合并转换服务程序
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.workflowmergeserver", "system.*", "api", "util", "dao"})
public class WorkflowMergeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowMergeServerApplication.class, args);
    }
}