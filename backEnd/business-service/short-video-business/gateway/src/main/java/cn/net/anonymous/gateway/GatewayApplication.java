package cn.net.anonymous.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 牛牛视界 - 网关服务
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.gateway", "common", "system"})
@MapperScan("cn.net.anonymous.gateway.module.*.mapper")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}