package dao.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import config.JsonConfig;
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
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 帖子实体
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Document("doc_post")
@CompoundIndexes(
        @CompoundIndex(
                name = "shares_likes_time_delete_usable_draft_content_post_by_playlist",
                def = "{shares: -1, likes: -1, gmt_create: -1, " +
                        "is_delete: -1, is_usable: -1, is_draft: 1, " +
                        "content: 1, post_by: 1, playlist_url: 1}")
)
public class Post
        extends BaseData
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 帖子id */
    @Id
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    @JsonProperty("post_id")
    private ObjectId postId;

    /** 帖子发布人 */
    @Field("post_by")
    @JsonProperty("post_by")
    private Long postBy;

    /** 帖子内容 */
    @Field("content")
    private String content;

    /** 播放列表 url */
    @Field("playlist_url")
    @JsonProperty("playlist_url")
    private String playlistUrl;

    /** 资源 url 列表 */
    @Field("resource_url")
    @JsonIgnore
    private List<String> resourceUrl;

    /** 点赞人数 */
    @Field("likes")
    private Long likes;

    /** 分享次数 */
    @Field("shares")
    private Long shares;

    /** 是否为草稿 */
    @Field("is_draft")
    @JsonIgnore
    private Integer isDraft;

    /** (资源) 是否可用 */
    @Field("is_usable")
    @JsonIgnore
    private Integer isUsable;

    public Post() {
        this.isDraft = 1;
        this.resourceUrl = new ArrayList<>();
        this.likes = 0L;
        this.shares = 0L;
    }
}