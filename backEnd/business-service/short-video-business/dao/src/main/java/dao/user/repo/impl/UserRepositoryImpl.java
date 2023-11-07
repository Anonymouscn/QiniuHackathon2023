package dao.user.repo.impl;

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

    /**
     * 分页查询用户信息
     *
     * @param no      页码
     * @param size    页面大小
     * @param keyword 关键字
     * @return 用户分页信息
     */
    @Override
    public Page<User> queryUserProfile(Integer no, Integer size, String keyword) {
        Query query = new Query();
        if(keyword != null) {
            query.addCriteria(Criteria.where("nickname")
                    .regex("^" + keyword + ".*$"));
        }
        List<User> users = mongoTemplate.find(
                query.skip((long) (no - 1) * size)
                        .limit(size),
                User.class
        );
        long total = mongoTemplate.count(query, User.class);
        return new Page<User>()
                .setNo(no)
                .setSize(size)
                .setRecords(users)
                .setTotal(total);
    }
}