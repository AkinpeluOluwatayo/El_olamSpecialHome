package enterprise.elroi.dto.requests;

import lombok.Data;
import java.util.List;

@Data
public class MediaRequest {
    private String childId;
    private List<String> base64Images;
}