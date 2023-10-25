package cn.net.anonymous.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 牛牛视界 - 日志服务
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.log", "common", "system"})
@MapperScan("cn.net.anonymous.log.module.*.mapper")
public class LogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }

}