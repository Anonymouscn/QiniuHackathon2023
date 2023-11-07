package cn.net.anonymous.gateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 牛牛视界 - 网关服务
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.gateway", "api", "util", "system", "config"})
public class GatewayApplication implements WebMvcConfigurer {

    @Value("${spring.profiles.active}")
    private String mode;

    @Override
    public void addResourceHandlers(@SuppressWarnings("NullableProblems") ResourceHandlerRegistry registry) {
        // 开发或测试模式下启用 API 文档
        if ("dev".equals(mode) || "test".equals(mode)) {
            registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}