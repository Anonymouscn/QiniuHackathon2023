package cn.net.anonymous.gateway.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 配置
 *
 * @author anonymous
 * @version 1.0
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port}")
    private Integer port;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info()
                        .title("牛牛视界 API 文档")
                        .description("牛牛视界 API 文档 - 开发版")
                        .version("1.0.0")
                        .contact(new Contact().name("匿名者冲锋战队"))
                        .termsOfService("http://localhost:" + port)
                        .license(new License().name("Apache 2.0"))
                ).externalDocs(new ExternalDocumentation()
                        .description("API 文档")
                        .url("https://anonymous.net.cn")
        );
    }
}