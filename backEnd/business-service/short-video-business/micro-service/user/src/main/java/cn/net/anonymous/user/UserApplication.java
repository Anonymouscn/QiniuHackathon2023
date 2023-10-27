package cn.net.anonymous.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
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
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("dao.user.mapper")
@ComponentScan({"cn.net.anonymous.user", "dao.*.mapper", "system", "api", "util"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}