package enterprise.elroi.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProgressReportRequest {
    private String childId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String observations;
    private String therapyNotes;
    private String milestones;
    private String createdBy;
}