package enterprise.elroi.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "parents")
public class Parents {
    @Id
    private String id;
    private String parentName;
    private String email;

}
