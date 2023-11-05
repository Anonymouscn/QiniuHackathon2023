package dao.post.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import config.JsonConfig;
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

/**
 * 帖子<->标签 分类关系文档
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@Document("doc_classification_post_tag")
@CompoundIndexes(
        value = {
                @CompoundIndex(name = "idx_post_tag", def = "{post_id:1, tag_id: 1}"),
                @CompoundIndex(name = "idx_tag_post", def = "{tag_id: 1, post_id: 1}")
        }
)
public class Classification
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 分类 id */
    @Id
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    private ObjectId classificationId;

    /** 帖子 id */
    @Field("post_id")
    private ObjectId postId;

    /** 标签 id */
    @Field("tag_id")
    private ObjectId tagId;
}