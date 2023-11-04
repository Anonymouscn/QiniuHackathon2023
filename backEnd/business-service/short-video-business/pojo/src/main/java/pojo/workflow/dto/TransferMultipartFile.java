package pojo.workflow.dto;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

/**
 * rpc 传输文件
 *
 * @author anonymous
 * @version 1.0
 */
public class TransferMultipartFile
        implements MultipartFile, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String filename;

    private final byte[] content;

    private String originFilename;

    @Nullable
    private String contentType;

    public TransferMultipartFile(String filename, @Nullable byte[]content) {
        this(filename, "", null, content);
    }

    public TransferMultipartFile(String filename, InputStream stream) throws IOException {
        this(filename, "", null, FileCopyUtils.copyToByteArray(stream));
    }

    public TransferMultipartFile(String filename, @Nullable String originFilename, @Nullable String contentType, @Nullable byte[] content) {
        Assert.hasLength(filename, "[filename] can't not be null");
        this.filename = filename;
        this.originFilename = originFilename;
        this.contentType = contentType;
        this.content = content != null ? content : new byte[0];
    }

    public TransferMultipartFile(String filename, @Nullable String originFilename, @Nullable String contentType, InputStream stream) throws IOException {
        this(filename, originFilename, contentType, FileCopyUtils.copyToByteArray(stream));
    }

    @Override
    public String getName() {
        return this.filename;
    }

    @Override
    public String getOriginalFilename() {
        return this.originFilename;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return this.content.length == 0;
    }

    @Override
    public long getSize() {
        return this.content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.content, dest);
    }
}