package enterprise.elroi.dto.requests;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ChildRequest {
    private String name;
    private String age;
    private LocalDate dateOfBirth;
    private String condition;
    private String medicalHistory;
}