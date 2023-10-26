package mapper;

import entity.Tag;
import org.springframework.stereotype.Repository;

/**
 * 内容标签数据接口
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
public interface TagMapper
        extends CustomerMapper<Tag> {

}