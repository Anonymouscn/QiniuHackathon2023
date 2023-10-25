package cn.net.anonymous.post.module.tag.mapper;

import cn.net.anonymous.post.module.tag.enntity.Tag;
import common.mapper.CustomerMapper;
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