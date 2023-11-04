package pojo.workflow.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 服务器信息
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class ServerInfo
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "ip")
    private String[] ip;

    @JsonProperty(value = "port")
    private Integer port;

    @JsonProperty(value = "auth")
    private String auth;
}