package system.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
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

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info()
                        .title("牛牛视界 API 文档")
                        .description("牛牛视界 API 文档 - 开发版")
                        .version("v1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://spring.io")
                        )
                ).externalDocs(new ExternalDocumentation()
                        .description("API 文档")
                        .url("")
        );
    }
}