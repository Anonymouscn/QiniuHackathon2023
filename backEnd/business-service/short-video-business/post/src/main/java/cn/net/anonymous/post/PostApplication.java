package cn.net.anonymous.post;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * 牛牛视界 - 帖子服务
 *
 * @author anonymous
 * @version 1.0
 */
@EnableWebSocket
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.post", "common", "system"})
@MapperScan("cn.net.anonymous.post.module.*.mapper")
public class PostApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }
}