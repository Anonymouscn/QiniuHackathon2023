package dao.user.mapper;

import dao.common.mapper.CustomerMapper;
import dao.user.entity.Avatar;
import org.springframework.stereotype.Repository;

/**
 * 头像信息数据接口
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
public interface AvatarMapper
        extends CustomerMapper<Avatar> {

}