package dao.post.repo.impl;

import com.google.common.base.Strings;
import dao.post.entity.Classification;
import dao.post.entity.Tag;
import dao.post.repo.TagRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pojo.common.vo.Page;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 标签数据接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl
        implements TagRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * 分页获取热门标签
     *
     * @param no   页码
     * @param size 页面大小
     * @param keyword 关键字
     * @return 人们标签分页数据
     */
    @Override
    public Page<Tag> queryHotTag(Integer no, Integer size, String keyword) {
        Query query = new Query()
                .with(Sort.by(Sort.Order.desc("click_index")))
                .skip((long) (no - 1) * size)
                .limit(size);
        if(!Strings.isNullOrEmpty(keyword)) {
            query.addCriteria(
                    Criteria.where("name")
                            .regex(Pattern.compile("^.*" + keyword + ".*$"))
            );
        }
        List<Tag> records = mongoTemplate.find(query, Tag.class);
        long count = mongoTemplate.count(query, Tag.class);
        return new Page<Tag>()
                .setRecords(records)
                .setNo(no)
                .setSize(size)
                .setTotal(count % size == 0 ? 1 : count / size + 1);
    }

    /**
     * 添加标签
     *
     * @param names 标签名列表
     * @return 标签
     */
    @Override
    public List<Tag> addTag(String[] names) {
        List<Tag> list = Arrays
                .stream(names)
                .parallel()
                .map(s -> new Tag().setName(s))
                .toList();
        return mongoTemplate.insertAll(list)
                .stream()
                .parallel()
                .toList();
    }

    /**
     * 统计标签
     *
     * @param tagList 标签列表
     * @return 是否统计成功
     */
    @Override
//    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean summaryTag(List<String> tagList, ObjectId postId) {
        List<Tag> insertList = Collections.synchronizedList(new ArrayList<>());
        tagList.stream()
                .parallel()
                .forEach(s -> {
                    Tag tag = mongoTemplate.findOne(Query.query(Criteria.where("name").is(s)), Tag.class);
                    // 已存在标签更新引用次数
                    if(tag != null) {
                        tag.setRefs(tag.getRefs() + 1);
                        mongoTemplate.save(tag);
                        long count = mongoTemplate.count(
                                Query.query(
                                        Criteria.where("post_id")
                                                .is(postId)
                                                .and("tag_id")
                                                .is(tag.getTagId())
                                ),
                                Classification.class,
                                "doc_classification_post_tag");
                        System.out.println(count);
                        if(count == 0) {
                            mongoTemplate.save(new Classification().setTagId(tag.getTagId()).setPostId(postId));
                        }
                    // 不存在则创建标签
                    } else {
                        insertList.add(new Tag().setName(s).setRefs(1L));
                    }
                });
        // 批量插入新标签
        if(!insertList.isEmpty()) {
            mongoTemplate.insert(insertList, "doc_tag");
            mongoTemplate.insert(
                    insertList.stream()
                            .parallel()
                            .map(s -> new Classification()
                                    .setTagId(s.getTagId())
                                    .setPostId(postId))
                            .collect(Collectors.toList()), "doc_classification_post_tag"
            );
        }
        return true;
    }

    /**
     * 删除标签
     *
     * @param tagId 标签 id 列表
     * @return 是否删除成功
     */
//    @Transactional
    @Override
    public boolean DropTag(String[] tagId) {
        if(mongoTemplate.remove(
                Query.query(
                        Criteria.where("tagId")
                                .in(tagId, Tag.class)
                                .and("is_delete")
                                .is(0)
                )
        ).getDeletedCount() != tagId.length) {
            throw new RuntimeException("标签删除失败 - 删除数据不一致");
        }
        return true;
    }
}