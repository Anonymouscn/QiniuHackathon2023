package dao.file.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文件信息表
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@Document("doc_file")
public class File
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 文件 id */
    @Id
    private String fileId;

    /** key on oss */
    @Field("key")
    private String key;

    /** hash on oss */
    @Field("hash")
    private String hash;

    /** 文件访问链接 */
    @Field("url")
    private String url;

    /** 文件草稿标记 */
    @Field("draft_flag")
    private Integer draftFlag;

    /** 关联文件资源 */
    @Field("resource")
    private List<File> resource;

    public File() {
        this.fileId = UUID.randomUUID().toString();
        this.resource = new ArrayList<>();
    }
}