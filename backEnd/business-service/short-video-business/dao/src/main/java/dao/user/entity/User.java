package dao.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import system.config.JsonConfig;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息实体
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@CompoundIndexes(
        @CompoundIndex(
                name = "idx_phone_password_nickname_avatar_sex_ban",
                def = "{phone: 1, password: 1, nickname: 1, avatar_url: 1, sex: 1, is_banned: 1}"
        )
)
@Document("doc_user")
public class User
        extends BaseData
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @Id
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    @JsonProperty("user_id")
    private ObjectId userId;

    /** 昵称 */
    @Field("nickname")
    @Indexed(unique = true)
    private String nickname;

    /** 手机号 - reverse */
    @Field("phone")
    @Indexed(unique = true)
    private String phone;

    /** 密码 */
    @JsonIgnore
    @Field("password")
    private String password;

    /** 性别 */
    @Field("sex")
    private Integer sex;

    /** 是否被封禁 */
    @JsonIgnore
    @Field("is_banned")
    private Integer isBanned;

    /** 头像链接 - reverse */
    @Field("avatar_url")
    @JsonProperty("avatar_url")
    private String avatarUrl;

    /** 被关注总数 */
    @Field("watched")
    @Indexed(name = "idx_watched")
    private Integer watched;
}