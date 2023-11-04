package pojo.post.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pojo.workflow.vo.ServerInfo;
import java.io.Serial;
import java.io.Serializable;

/**
 * 帖子 vo
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class PostVo
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 帖子 id */
    @JsonProperty(value = "post_id")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String postId;

    /** 上传服务器 */
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "upload_server")
    private ServerInfo uploadServer;

    /** 上传用户 id */
    @JsonProperty(value = "post_by")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Long postBy;

    /** 帖子内容 */
    @JsonProperty(value = "content")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String content;

    /** 播放列表 url */
    @JsonProperty(value = "playlist_url")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String playlistUrl;

    /** 点赞人数 */
    @JsonProperty(value = "likes")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Long likes;
}