package enterprise.elroi.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Document(collection = "media")
public class Media {

    @Id
    private String id;

    private String childId;

    private ArrayList<String> images;

    private LocalDateTime uploadDate;
}