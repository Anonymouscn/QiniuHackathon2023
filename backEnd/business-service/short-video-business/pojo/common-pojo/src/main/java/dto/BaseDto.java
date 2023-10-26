package dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class BaseDto
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}