package enterprise.elroi.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "children")
public class Child {

    @Id
    private String id;
    private String name;
    private String age;
    private LocalDate dateOfBirth;
    private String condition;
    private String medicalHistory;

    // Tracking for CEO reports
    private LocalDateTime enrollmentDate = LocalDateTime.now();
}