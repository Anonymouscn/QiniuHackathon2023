package dao.file.repo.impl;

import dao.file.entity.File;
import dao.file.repo.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * 文件信息数据接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl
        implements FileRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * 获取文件信息
     *
     * @param fileId 文件 id
     * @return 文件信息
     */
    @Override
    public File getFile(String fileId) {
        Query query = new Query(Criteria.where("fileId").is(fileId));
        return mongoTemplate.findOne(query, File.class);
    }

    /**
     * 保存文件
     *
     * @param file 文件信息
     * @return 文件 id
     */
    @Override
    public String saveFile(File file) {
        mongoTemplate.save(file);
        return file.getFileId();
    }

    /**
     * 删除文件
     *
     * @param fileId 文件 id
     * @return 是否删除成功
     */
    @Override
    public boolean deleteFile(String fileId) {
        Query query = new Query(Criteria.where("fileId").is(fileId));
        return mongoTemplate.remove(query, File.class).getDeletedCount() > 0;
    }
}