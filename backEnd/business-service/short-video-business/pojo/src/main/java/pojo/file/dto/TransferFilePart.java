package pojo.file.dto;

import org.springframework.http.codec.multipart.FilePart;
import java.io.Serial;
import java.io.Serializable;

public record TransferFilePart(FilePart file) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}