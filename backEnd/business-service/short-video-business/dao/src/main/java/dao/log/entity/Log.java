package dao.log.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
 * 日志信息
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
@CompoundIndexes(
        @CompoundIndex(
                name = "idx_level_source_machine_archived_content",
                def = "{level: -1, source: 1, machine_id: 1, is_archived: 1, content: 1}"
        )
)
@Document("doc_log")
public class Log
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 日志 id */
    @Id
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    @JsonProperty("log_id")
    private ObjectId logId;

    /** 机器 id */
    @Field("machine_id")
    private String machineId;

    /** 来源类 */
    @Field("source")
    private String source;

    /** 日志等级 */
    @Field("level")
    private String level;

    /** 日志内容 */
    @Field("content")
    private String content;

    /** 是否已归档 */
    @Field("is_archived")
    private Integer isArchived;
}