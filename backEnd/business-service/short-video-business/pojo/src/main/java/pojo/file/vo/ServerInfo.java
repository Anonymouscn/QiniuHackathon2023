package pojo.file.vo;

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

    private String[] ip;

    private Integer port;

    private String auth;
}