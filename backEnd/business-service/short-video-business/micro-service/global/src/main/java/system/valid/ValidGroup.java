package system.valid;

/**
 * validation 分组校验
 *
 * @author anonymous
 * @version 1.0
 */
public interface ValidGroup {

    /** 业务查询 */
    interface Query {}

    /** 业务插入 */
    interface Insert {}

    /** 业务更新 */
    interface Update {}

    /** 业务删除 */
    interface Delete {}

    interface Registry {}

    interface Login {}
}