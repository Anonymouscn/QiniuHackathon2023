package api.user;

import dao.user.entity.User;
import pojo.common.vo.Page;
import pojo.user.dto.UserDto;
import pojo.workflow.vo.ServerInfo;

/**
 * 用户服务接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface IUserService {

    /**
     * 用户注册
     *
     * @param userDto 用户 dto
     * @return 新用户 id
     */
    String registryBySms(UserDto userDto);

    /**
     * 用户短信登录
     *
     * @param userDto 用户 dto
     * @return token
     */
    String loginBySms(UserDto userDto);

    /**
     * 用户登出
     *
     * @param userId 用户 id
     * @return 是否登出成功
     */
    boolean logout(String userId);

    /**
     * 重置密码
     *
     * @param userDto 用户 dto
     * @return 是否重置成功
     */
    boolean resetPassword(UserDto userDto);

    /**
     * 更换手机号
     *
     * @param userDto 用户 dto
     * @return 是否更换成功
     */
    boolean resetPhone(UserDto userDto);

    /**
     * 申请服务器上传头像
     *
     * @param userId 用户 id
     * @return 服务器信息
     */
    ServerInfo getServerForAvatarUpload(String userId);

    /**
     * 获取用户信息
     *
     * @param userId 用户 id
     * @return 用户信息
     */
    User getUserInfo(String userId);

    /**
     * 更新个人信息
     *
     * @param userDto 用户 dto
     * @return 是否更新成功
     */
    boolean updateProfile(UserDto userDto);

    /**
     * 分页查询用户信息
     *
     * @param no 页码
     * @param size 页面大小
     * @param keyword 关键字
     * @return 用户分页信息
     */
    Page<User> queryUser(Integer no, Integer size, String keyword);
}