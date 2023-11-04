package dao.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dao.common.entity.BaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import system.config.JsonConfig;
import java.io.Serial;
import java.io.Serializable;

/**
 * 点赞信息实体
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@CompoundIndexes(
        value = {
                @CompoundIndex(name = "idx_post_user", def = "{post_id: 1, user_id: 1}"),
                @CompoundIndex(name = "idx_user_post", def = "{user_id: 1, post_id: 1}")
        }
)
@Document("doc_likes")
public class Likes
        extends BaseData
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 点赞数据 id */
    @Id
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    private ObjectId likesId;

    /** 帖子 id */
    @Field("post_id")
    private ObjectId postId;

    /** 用户 id */
    @Field("user_id")
    private ObjectId userId;
}