package cn.net.anonymous.post.module.tag.enntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import common.entity.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 内容标签实体
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_tag")
public class Tag
        extends Base
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 标签id */
    @TableId(type = IdType.ASSIGN_ID, value = "pk_tag_id")
    private Long id;

    /** 标签名 */
    @TableField(value = "name")
    private String name;
}