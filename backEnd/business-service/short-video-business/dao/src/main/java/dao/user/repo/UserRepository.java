package dao.user.repo;

import dao.user.entity.User;
import pojo.user.dto.UserDto;

/**
 * 用户信息数据接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface UserRepository {

    /**
     * 创建用户
     *
     * @param userDto 用户 dto
     * @return 用户信息
     */
    User createUser(UserDto userDto);

    /**
     * 获取用户信息
     *
     * @param userId 用户 id
     * @return 用户信息
     */
    User getUserProfile(String userId);

    /**
     * 更新用户信息
     *
     * @param userDto 用户 dto
     * @return 是否更新成功
     */
    boolean UpdateUserProfile(UserDto userDto);
}