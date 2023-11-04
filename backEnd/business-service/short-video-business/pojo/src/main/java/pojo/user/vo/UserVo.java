package pojo.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class UserVo
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


}