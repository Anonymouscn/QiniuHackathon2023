package cn.net.anonymous.post.service.impl;

import api.post.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dao.post.entity.Post;
import dao.post.mapper.PostMapper;
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