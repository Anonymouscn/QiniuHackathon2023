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
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serial;
import java.io.Serializable;

/**
 * 内容标签实体
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@CompoundIndexes(
        @CompoundIndex(
                name = "idx_click_index_name",
                def = "{click_index: -1, name: 1}"
        )
)
@Document("doc_tag")
public class Tag
        extends BaseData
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 标签id */
    @Id
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    @JsonProperty("tag_id")
    private ObjectId tagId;

    /** 标签名 */
    @Indexed(unique = true)
    @Field("name")
    private String name;

    /** 点击指数 */
    @JsonIgnore
    @Field("click_index")
    private Long clickIndex;

    /** 引用次数 */
    @JsonIgnore
    @Field("refs")
    private Long refs;
}