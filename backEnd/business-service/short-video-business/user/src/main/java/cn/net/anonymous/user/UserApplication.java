package cn.net.anonymous.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 牛牛视界 - 用户服务
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("cn.net.anonymous.user.module.*.mapper")
@ComponentScan({"cn.net.anonymous.user", "common", "system"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}