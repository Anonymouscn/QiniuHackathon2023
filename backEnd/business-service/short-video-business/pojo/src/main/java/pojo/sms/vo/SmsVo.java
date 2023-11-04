package pojo.sms.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * sms vo
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class SmsVo
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 校验 key */
    private String key;
}