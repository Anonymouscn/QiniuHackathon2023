package system.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.common.serialize.fastjson2.Fastjson2CreatorManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig extends DelegatingWebMvcConfiguration {

    private final Jackson2ObjectMapperBuilder jacksonBuilder;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        var applicationContext = this.getApplicationContext();
        if (applicationContext != null) {
            jacksonBuilder.applicationContext(applicationContext);
        }
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter(jacksonBuilder.build()));
//        converters.add(new FastJsonHttpMessageConverter());
//        converters.add(new ByteArrayHttpMessageConverter());
//        WebMvcConfigurer.super.configureMessageConverters(converters);
    }
}
