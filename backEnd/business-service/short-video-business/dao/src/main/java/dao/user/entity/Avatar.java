package dao.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import dao.common.entity.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 头像信息实体
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "tb_avatar")
public class Avatar
        extends Base
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 头像id */
    @TableId(type = IdType.ASSIGN_ID, value = "pk_avatar_id")
    private Long avatarId;

    /** 用户id */
    @TableField(value = "fk_user_id")
    private Long userId;

    /** 文件id */
    @TableField(value = "fk_file_id")
    private Long fileId;

    /** 头像链接 */
    @TableField(value = "url")
    private String url;

    /** 链接长度 */
    @TableField(value = "len")
    private Integer len;
}