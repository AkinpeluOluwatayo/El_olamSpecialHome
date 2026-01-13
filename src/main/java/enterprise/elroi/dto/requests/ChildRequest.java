package enterprise.elroi.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ChildRequest {
    private String name;
    private String age;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String condition;
    private String medicalHistory;
}