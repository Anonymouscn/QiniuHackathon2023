package mapper;

import org.springframework.stereotype.Repository;

/**
 * 用户消息数据接口
 *
 * @author anonymous
 * @version 1.0
 *
 * @param <T> 参数实体
 */
@Repository
public interface MessageMapper<T>
        extends CustomerMapper<T> {

}