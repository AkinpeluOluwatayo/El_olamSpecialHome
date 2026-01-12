package enterprise.elroi.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ChildResponse {
    private String id;
    private String name;
    private String age;
    private LocalDate dateOfBirth;
    private String condition;
    private String medicalHistory;
}