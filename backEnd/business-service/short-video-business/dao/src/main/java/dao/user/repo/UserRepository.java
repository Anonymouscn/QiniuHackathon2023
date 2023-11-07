package dao.user.repo;

import dao.user.entity.User;
import pojo.common.vo.Page;
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

    /**
     * 分页查询用户信息
     *
     * @param no 页码
     * @param size 页面大小
     * @param keyword 关键字
     * @return 用户分页信息
     */
    Page<User> queryUserProfile(Integer no, Integer size, String keyword);
}