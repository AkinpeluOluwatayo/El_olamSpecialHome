package enterprise.elroi.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MediaResponse {
    private String id;
    private String childId;
    private List<String> images;
    private LocalDateTime uploadDate;
}