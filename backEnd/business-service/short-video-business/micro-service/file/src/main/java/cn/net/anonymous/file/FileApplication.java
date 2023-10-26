package cn.net.anonymous.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 牛牛视界 - 文件服务
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"cn.net.anonymous.file", "system", "api", "util"})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}