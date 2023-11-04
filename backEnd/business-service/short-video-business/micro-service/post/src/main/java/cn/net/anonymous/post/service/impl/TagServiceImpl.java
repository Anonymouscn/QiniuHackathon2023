package cn.net.anonymous.post.service.impl;

import api.post.ITagService;
import dao.post.entity.Tag;
import dao.post.repo.TagRepository;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import pojo.common.vo.Page;
import java.util.List;

/**
 * 内容标签服务实现
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
@RequiredArgsConstructor
public class TagServiceImpl
        implements ITagService {

    private final TagRepository tagRepository;

    /**
     * 分页获取/搜索热门标签
     *
     * @param no      页码
     * @param size    页面大小
     * @param keyword 关键字
     * @return 热门标签分页信息
     */
    @Override
    public Page<Tag> queryHotTag(Integer no, Integer size, String keyword) {
        return tagRepository.queryHotTag(no, size, keyword);
    }

    /**
     * 添加标签
     *
     * @param names 标签名列表
     * @return 标签
     */
    @Override
    public List<Tag> addTag(String[] names) {
        return tagRepository.addTag(names);
    }

    /**
     * 删除标签
     *
     * @param tagId 标签 id 列表
     * @return 是否删除成功
     */
    @Override
    public boolean DropTag(String[] tagId) {
        return tagRepository.DropTag(tagId);
    }
}