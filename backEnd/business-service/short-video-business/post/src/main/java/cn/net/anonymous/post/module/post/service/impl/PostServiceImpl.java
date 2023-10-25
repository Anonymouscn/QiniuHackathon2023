package cn.net.anonymous.post.module.post.service.impl;

import cn.net.anonymous.post.module.post.entity.Post;
import cn.net.anonymous.post.module.post.mapper.PostMapper;
import cn.net.anonymous.post.module.post.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 视频内容接口实现类
 *
 * @author anonymous
 * @version 1.0
 */
@Service
public class PostServiceImpl
        extends ServiceImpl<PostMapper, Post>
        implements IPostService {

}