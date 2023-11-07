package dao.post.repo;

import dao.post.entity.Tag;
import org.bson.types.ObjectId;
import pojo.common.vo.Page;
import java.util.List;

/**
 * 标签数据接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface TagRepository {

    /**
     * 分页获取/搜索热门标签
     *
     * @param no 页码
     * @param size 页面大小
     * @param keyword 关键字
     * @return 热门标签分页信息
     */
    Page<Tag> queryHotTag(Integer no, Integer size, String keyword);

    /**
     * 添加标签
     *
     * @param names 标签名列表
     * @return 标签
     */
    List<Tag> addTag(String[] names);

    /**
     * 统计标签
     *
     * @param tagList 标签列表
     * @return 是否统计成功
     */
    boolean summaryTag(List<String> tagList, ObjectId postId);

    /**
     * 删除标签
     *
     * @param tagId 标签 id 列表
     * @return 是否删除成功
     */
    boolean DropTag(String[] tagId);
}