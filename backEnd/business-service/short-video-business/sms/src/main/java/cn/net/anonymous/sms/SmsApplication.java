package cn.net.anonymous.sms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 牛牛视界 - 短信服务
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.sms", "common", "system"})
@MapperScan("cn.net.anonymous.sms.module.*.mapper")
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }

}