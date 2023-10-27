package api.post;

import com.baomidou.mybatisplus.extension.service.IService;
import dao.post.entity.Post;
import org.springframework.stereotype.Repository;

/**
 * 视频内容服务接口
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
public interface IPostService
        extends IService<Post> {

}