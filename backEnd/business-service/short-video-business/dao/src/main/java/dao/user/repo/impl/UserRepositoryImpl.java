package dao.user.repo.impl;

import com.google.common.base.Strings;
import dao.post.entity.Post;
import dao.user.entity.Collects;
import dao.user.entity.Likes;
import dao.user.entity.User;
import dao.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import pojo.common.vo.Page;
import pojo.user.dto.UserDto;
import java.util.List;
import java.util.UUID;

/**
 * 用户信息数据接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl
        implements UserRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * 创建用户
     *
     * @param userDto 用户 dto
     * @return 用户信息
     */
    @Override
    public User createUser(UserDto userDto) {
        return mongoTemplate.insert(
                new User().setPhone(userDto.getPhone())
                        .setPassword(userDto.getPassword())
                        // 用户设置随机 uuid 作为初始名字
                        .setNickname("用户-" + UUID.randomUUID())
        );
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户 id
     * @return 用户信息
     */
    @Override
    public User getUserProfile(String userId) {
        return mongoTemplate.findById(new ObjectId(userId), User.class);
    }

    /**
     * 更新用户信息
     *
     * @param userDto 用户 dto
     * @return 是否更新成功
     */
    @Override
    public boolean UpdateUserProfile(UserDto userDto) {
        Query query = Query.query(Criteria.where("_id").is(userDto.getUserId()));
        Update update = new Update();
        if(userDto.getNickname() != null) update.set("nickname", userDto.getNickname());
        if(userDto.getSex() != null) update.set("sex", userDto.getSex());
        return mongoTemplate.updateFirst(query, update, "doc_user").getModifiedCount() > 0;
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
//        List<Likes> likes = mongoTemplate.find(
//                Query.query(
//                        Criteria.where("user_id")
//                                .is(new ObjectId(userId))
//                ), Likes.class
//        );
//        List<ObjectId> list = likes.parallelStream()
//                .map(Likes::getPostId)
//                .toList();
//        Criteria criteria = new Criteria("_id").in(list);
//        if(!Strings.isNullOrEmpty(keyword))
//            criteria.and("content")
//                    .regex(".*?\\" + keyword + ".*");
//        Query query = new Query(criteria).skip((long) (no - 1) * size).limit(size);
//        long count = mongoTemplate.count(new Query(criteria), Post.class);
//        return new Page<Post>()
//                .setNo(no)
//                .setSize(size)
//                .setTotal(count)
//                .setRecords(mongoTemplate.find(query, Post.class));
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
//        List<Collects> collects = mongoTemplate.find(
//                Query.query(
//                        Criteria.where("user_id")
//                                .is(new ObjectId(userId))
//                ), Collects.class
//        );
//        List<ObjectId> list = collects.parallelStream()
//                .map(Collects::getPostId)
//                .toList();
//        Criteria criteria = new Criteria("_id").in(list);
//        if(!Strings.isNullOrEmpty(keyword))
//            criteria.and("content")
//                    .regex(".*?\\" + keyword + ".*");
//        Query query = new Query(criteria).skip((long) (no - 1) * size).limit(size);
//        long count = mongoTemplate.count(new Query(criteria), Post.class);
//        return new Page<Post>()
//                .setNo(no)
//                .setSize(size)
//                .setTotal(count)
//                .setRecords(mongoTemplate.find(query, Post.class));
//    }
}