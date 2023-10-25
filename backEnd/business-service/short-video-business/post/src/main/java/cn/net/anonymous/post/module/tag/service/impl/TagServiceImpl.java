package cn.net.anonymous.post.module.tag.service.impl;

import cn.net.anonymous.post.module.tag.enntity.Tag;
import cn.net.anonymous.post.module.tag.mapper.TagMapper;
import cn.net.anonymous.post.module.tag.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 内容标签服务实现
 *
 * @author anonymous
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl
        extends ServiceImpl<TagMapper, Tag>
        implements ITagService {

    private final TagMapper tagMapper;
}