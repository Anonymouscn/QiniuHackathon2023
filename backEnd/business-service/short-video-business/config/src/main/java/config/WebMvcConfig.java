package config;

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import java.util.List;

/**
 * webmvc jackson 配置
 *
 * @author anonymous
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig extends DelegatingWebMvcConfiguration {

    private final Jackson2ObjectMapperBuilder jacksonBuilder;

    @Override
    public void configureMessageConverters(@SuppressWarnings("NullableProblems")
                                               List<HttpMessageConverter<?>> converters) {
        var applicationContext = this.getApplicationContext();
        if (applicationContext != null) {
            jacksonBuilder.applicationContext(applicationContext);
        }
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter(jacksonBuilder.build()));
    }
}