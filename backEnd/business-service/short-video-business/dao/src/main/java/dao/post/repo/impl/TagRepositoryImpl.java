package dao.post.repo.impl;

import com.google.common.base.Strings;
import dao.post.entity.Tag;
import dao.post.repo.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pojo.common.vo.Page;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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
     * 删除标签
     *
     * @param tagId 标签 id 列表
     * @return 是否删除成功
     */
    @Transactional
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