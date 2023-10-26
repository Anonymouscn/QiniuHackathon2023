package cn.net.anonymous.post.service.impl;

import api.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.Post;
import mapper.PostMapper;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 视频内容接口实现类
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
public class PostServiceImpl
        extends ServiceImpl<PostMapper, Post>
        implements IPostService {

}