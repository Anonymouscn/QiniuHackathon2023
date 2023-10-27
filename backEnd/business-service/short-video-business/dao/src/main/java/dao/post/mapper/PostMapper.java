package dao.post.mapper;

import dao.common.mapper.CustomerMapper;
import dao.post.entity.Post;
import org.springframework.stereotype.Repository;

/**
 * 帖子信息数据接口
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
public interface PostMapper
        extends CustomerMapper<Post> {

}