package cn.net.anonymous.user.service.impl;

import api.sms.ISmsService;
import api.user.IUserService;
import dao.post.entity.Post;
import dao.user.entity.User;
import dao.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import pojo.common.vo.Page;
import pojo.sms.dto.SmsDto;
import pojo.user.dto.UserDto;
import pojo.workflow.vo.ServerInfo;

/**
 * 用户基本信息服务实现
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
@RequiredArgsConstructor
public class UserServiceImpl
        implements IUserService {

    private final UserRepository userRepository;

    @DubboReference
    private ISmsService smsService;

    /**
     * 用户短信注册
     *
     * @param userDto 用户 dto
     * @return 新用户 id
     */
    @Override
    public String registryBySms(UserDto userDto) {
        checkSms(userDto);
        return userRepository.createUser(userDto).getUserId().toHexString();
    }

    /* 校验短信验证码 */
    private void checkSms(UserDto userDto) {
        SmsDto smsDto = new SmsDto();
        BeanUtils.copyProperties(userDto, smsDto);
        if(!smsService.checkSms(smsDto)) {
            throw new RuntimeException("验证码不存在或已过期");
        }
    }

    /**
     * 用户短信登录
     *
     * @param userDto 用户 dto
     * @return token
     */
    @Override
    public String loginBySms(UserDto userDto) {
        checkSms(userDto);
        // 返回token
        return null;
    }

    /**
     * 用户登出
     *
     * @param userId 用户 id
     * @return 是否登出成功
     */
    @Override
    public boolean logout(String userId) {
        // 清除 token
        return false;
    }

    /**
     * 重置密码
     *
     * @param userDto 用户 dto
     * @return 是否重置成功
     */
    @Override
    public boolean resetPassword(UserDto userDto) {
        checkSms(userDto);
        // 重置密码
        return false;
    }

    /**
     * 更换手机号
     *
     * @param userDto 用户 dto
     * @return 是否更换成功
     */
    @Override
    public boolean resetPhone(UserDto userDto) {
        return false;
    }

    /**
     * 申请服务器上传头像
     *
     * @param userId 用户 id
     * @return 服务器信息
     */
    @Override
    public ServerInfo getServerForAvatarUpload(String userId) {
        return null;
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户 id
     * @return 用户信息
     */
    @Override
    public User getUserInfo(String userId) {
        return userRepository.getUserProfile(userId);
    }

    /**
     * 更新个人信息
     *
     * @param userDto 用户 dto
     * @return 是否更新成功
     */
    @Override
    public boolean updateProfile(UserDto userDto) {
        return userRepository.UpdateUserProfile(userDto);
    }

//    /**
//     * 获取/查询用户点赞的帖子
//     *
//     * @param no      页码
//     * @param size    页面大小
//     * @param keyword 关键词
//     * @return 用户点赞的帖子分页数据
//     */
//    @Override
//    public Page<Post> queryPostsLiked(Integer no, Integer size, String userId, String keyword) {
//        return userRepository.queryPostsLiked(no, size, userId, keyword);
//    }

//    /**
//     * 获取/查询用户收藏的帖子
//     *
//     * @param no      页码
//     * @param size    页面大小
//     * @param keyword 关键词
//     * @return 用户收藏的帖子分页数据
//     */
//    @Override
//    public Page<Post> queryPostsCollected(Integer no, Integer size, String userId, String keyword) {
//        return userRepository.queryPostsCollected(no, size, userId, keyword);
//    }
}