package enterprise.elroi.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "elolam_users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String role; // CEO, DIRECTOR, PARENT
    private String phoneNumber;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    private List<String> childrenIds = new ArrayList<>();
    private List<String> progressReportIds = new ArrayList<>();
}