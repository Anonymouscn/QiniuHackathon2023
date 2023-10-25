package cn.net.anonymous.post.module.post.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import common.entity.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 帖子实体
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Post
        extends Base
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 帖子id */
    @TableId(type = IdType.ASSIGN_ID, value = "pk_post_id")
    private Long id;

    /** 发布人id */
    @TableField(value = "post_by")
    private Long postBy;
}
