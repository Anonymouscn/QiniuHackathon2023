package dao.user.mapper;

import dao.common.mapper.CustomerMapper;
import dao.user.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户信息数据接口
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
public interface UserMapper
        extends CustomerMapper<User> {

}