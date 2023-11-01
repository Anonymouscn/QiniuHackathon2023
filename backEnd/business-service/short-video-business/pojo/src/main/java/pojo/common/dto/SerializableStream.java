package pojo.common.dto;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;

/**
 * 序列化流
 *
 * @author anonymous
 * @version 1.0
 */
public class SerializableStream
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final byte[] bytes;

    public SerializableStream(byte[] bytes) {
        this(bytes, -1);
    }

    public SerializableStream(byte[] bytes, int max) {
        int defaultMaxLength = 2048;
        int maxLength = max > 0 ? max : defaultMaxLength;
        if(bytes.length > maxLength)
            throw new IllegalArgumentException("Pack error: Data bytes exceed length");
        this.bytes = bytes.length > 0 ? bytes : new byte[0];
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }

    public byte[] getBytes() {
        return bytes;
    }
}