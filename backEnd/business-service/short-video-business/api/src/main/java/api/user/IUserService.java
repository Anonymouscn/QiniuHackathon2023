package api.user;

import dao.post.entity.Post;
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

//    /**
//     * 获取/查询用户点赞的帖子
//     *
//     * @param no 页码
//     * @param size 页面大小
//     * @param keyword 关键词
//     * @return 用户点赞的帖子分页数据
//     */
//    Page<Post> queryPostsLiked(Integer no, Integer size, String userId, String keyword);
//
//    /**
//     * 获取/查询用户收藏的帖子
//     *
//     * @param no 页码
//     * @param size 页面大小
//     * @param keyword 关键词
//     * @return 用户收藏的帖子分页数据
//     */
//    Page<Post> queryPostsCollected(Integer no, Integer size, String userId, String keyword);
}