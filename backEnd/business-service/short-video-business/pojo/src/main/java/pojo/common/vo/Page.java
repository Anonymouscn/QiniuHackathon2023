package pojo.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页页面
 *
 * @author anonymous
 * @version 1.0
 *
 * @param <T> 实体对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class Page<T>
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 数据记录 */
    private List<T> records;

    /** 当前页 */
    private Integer no;

    /** 页面大小 */
    private Integer size;

    /** 总记录数 */
    private Long total;
}