package dao.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 非结构化基础数据
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class BaseData
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 是否删除 */
    @Field("is_delete")
    @JsonIgnore
    private Integer isDelete;

    /** 数据创建时间 */
    @Field("gmt_create")
    @JsonIgnore
    private LocalDateTime gmtCreate;

    /** 数据最新更新时间 */
    @Field("gmt_update")
    @JsonIgnore
    private LocalDateTime gmtUpdate;

    /** 数据删除时间 */
    @Field("gmt_delete")
    @JsonIgnore
    private LocalDateTime gmtDelete;
}