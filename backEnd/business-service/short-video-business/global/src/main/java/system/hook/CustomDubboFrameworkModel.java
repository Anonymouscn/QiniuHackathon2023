package system.hook;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import java.util.Properties;

/**
 * 自定义 Dubbo - FrameworkModel
 *
 * @author anonymous
 * @version 1.0
 */
@Slf4j
@Configuration
@Primary
public class CustomDubboFrameworkModel
        extends FrameworkModel {

    private static final String path;

    static {
        YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
        factoryBean.setResources(new ClassPathResource("bootstrap.yml"));
        Properties properties = factoryBean.getObject();
        assert properties != null;
        path = (String) properties.get("dubbo.registry.cache-folder");
    }

    @Override
    protected void initialize() {
        // dubbo cache 文件路径环境配置 (fix: 在单机上运行时防止多个进程同时写缓存文件导致异常)
        log.info("Path to save dubbo cache files is: {}", path);
        System.setProperty("user.home",
                Strings.isNullOrEmpty(path) ?
                        System.getProperty("user.home") : path
        );
        super.initialize();
    }
}