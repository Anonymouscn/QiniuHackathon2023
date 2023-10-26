package cn.net.anonymous.websocketmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * websocket 监视器
 *
 * @author anonymous
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
public class WebsocketMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketMonitorApplication.class, args);
    }
}