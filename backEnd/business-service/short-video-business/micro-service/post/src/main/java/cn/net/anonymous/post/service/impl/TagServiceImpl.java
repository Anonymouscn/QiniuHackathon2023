package cn.net.anonymous.post.service.impl;

import api.post.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dao.post.entity.Tag;
import dao.post.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 内容标签服务实现
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
@RequiredArgsConstructor
public class TagServiceImpl
        extends ServiceImpl<TagMapper, Tag>
        implements ITagService {

    private final TagMapper tagMapper;
}