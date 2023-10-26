package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息实体
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "tb_user")
@Accessors(chain = true)
public class User
        extends Base
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @TableId(type = IdType.ASSIGN_ID, value = "pk_user_id")
    private Long id;

    /** 昵称 */
    @TableField(value = "nickname")
    private String nickname;

    /** 手机号 */
    @TableField(value = "phone")
    private String phone;

    /** 密码 */
    @TableField(value = "password")
    private String password;

    /** 性别 */
    @TableField(value = "sex")
    private Integer sex;

    /** 是否被封禁 */
    @TableField(value = "is_banned")
    private Integer isBanned;
}