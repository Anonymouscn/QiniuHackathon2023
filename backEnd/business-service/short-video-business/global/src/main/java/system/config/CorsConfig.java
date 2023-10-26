package system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置
 *
 * @author anonymous
 * @version 1.0
 */
@Configuration
public class CorsConfig
        implements WebMvcConfigurer {

    /**
     * 全局跨域配置
     *
     * @param registry 跨域注册
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 拦截所有的请求
        registry.addMapping("/**") // 可跨域的域名
                .allowedOrigins("*")
                .allowedMethods("*") // 允许跨域的请求头
                .allowedHeaders("*");
    }
}