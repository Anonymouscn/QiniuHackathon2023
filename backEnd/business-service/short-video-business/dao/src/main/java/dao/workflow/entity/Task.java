package dao.workflow.entity;

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
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serial;
import java.io.Serializable;

/**
 * 工作流任务
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Document("doc_task")
public class Task
        extends BaseData
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 任务 id */
    @Id
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    @JsonProperty("task_id")
    private ObjectId taskId;

    /** 任务状态 */
    @Field("state")
    private Integer state;

    /** 最新版本 */
    @Field("latest")
    private Integer latest;
}