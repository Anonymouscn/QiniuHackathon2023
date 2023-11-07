package cn.net.anonymous.post;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 牛牛视界 - 帖子服务
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.post", "api", "system", "util", "config", "dao"})
public class PostApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }
}