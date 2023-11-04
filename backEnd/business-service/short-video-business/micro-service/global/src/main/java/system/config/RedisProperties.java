package system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    private String password;

    private Cluster cluster;

    private Single single;



    @Data
    public static class Cluster {

        private List<String> addresses;
    }

    @Data
    public static class Single {

        private String address;

        private Integer database;
    }
}
