package dao.file.repo;

import dao.file.entity.File;

/**
 * 文件信息数据接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface FileRepository {

    /**
     * 获取文件信息
     *
     * @param fileId 文件 id
     * @return 文件信息
     */
    File getFile(String fileId);

    /**
     * 保存文件信息
     *
     * @param file 文件信息
     * @return 是否保存成功
     */
    String saveFile(File file);

    /**
     * 删除文件信息
     *
     * @param fileId 文件 id
     * @return 是否删除成功
     */
    boolean deleteFile(String fileId);
}