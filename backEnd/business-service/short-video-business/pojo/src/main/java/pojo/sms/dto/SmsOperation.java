package pojo.sms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * sms 操作
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class SmsOperation
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 手机号 */
    private String phone;

    /** 操作列表 map<OperateCode:timestamp> */
    private List<Map<Long, Integer>> operations;

    /** 最新更新时间 */
    private Long latest;

    /** 是否禁止发送短信 */
    private Boolean isBanned;
}