package entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通用实体类
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = false)
public class Base
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 是否已删除 */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    /** 是否可删除 */
    @TableField(value = "is_deletable")
    private Integer isDeletable;

    /** 创建时间 */
    @TableField(value = "gmt_create")
    private LocalDateTime gmtCreate;

    /** 更新时间 */
    @TableField(value = "gmt_update")
    private LocalDateTime gmtUpdate;

    /** 删除时间 */
    @TableField(value = "gmt_delete")
    private LocalDateTime gmtDelete;
}