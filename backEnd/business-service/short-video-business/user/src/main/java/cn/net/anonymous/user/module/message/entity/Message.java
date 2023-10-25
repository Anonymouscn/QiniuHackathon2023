package cn.net.anonymous.user.module.message.entity;

import common.entity.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户消息实体
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Message
        extends Base
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


}