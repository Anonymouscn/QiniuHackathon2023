package util;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 文件转换工具
 *
 * @author anonymous
 * @version 1.0
 */
public class FileUtil {

    /**
     * 强制删除 (递归删除) 文件或文件夹
     *
     * @param folder 文件夹
     * @return 是否删除成功
     */
    @SneakyThrows
    public static boolean forceRemoveFileOrDic(File file) {
        if(!file.exists()) return false;
        if(file.isFile()) return file.delete();
        File[] files = file.listFiles();
        if(files == null)
            return file.delete();
        for(File f : files) {
            if(f.isFile()) {
                f.delete();
            } else if(f.isDirectory()) {
                forceRemoveFileOrDic(f);
            }
        }
        return file.delete();
    }

    /**
     * MultipartFile 转 File
     *
     * @param multipartFile MultipartFile
     * @param pathToSave 文件存储路径
     * @return File 文件
     */
    @SneakyThrows
    public static File multipartFileToFile(MultipartFile multipartFile, String pathToSave) {
        File file = new File(pathToSave);
        try (OutputStream os = Files.newOutputStream(Path.of(pathToSave))) {
            os.write(multipartFile.getBytes());
        }
        return file;
    }

    /**
     * 获取有效目录
     *
     * @param pathToFolder 目录路径
     * @return 目录
     */
    @SneakyThrows
    public static File getFolder(String pathToFolder) {
        Path path = Paths.get(pathToFolder);
        File folder = path.toFile();
        // 目录不存在则自动创建目录
        if(!folder.exists())
            Files.createDirectories(path);
        if(folder.isFile() && folder.delete())
            Files.createDirectories(path);
        return folder;
    }

    public static String[] getFileInfo(String fullName) {
        int idx = fullName.lastIndexOf('.');
        if(idx > 0) return new String[] {
                fullName.substring(0, idx),
                fullName.substring(idx + 1)
        };
        return new String[] { fullName, "unknown" };
    }
}