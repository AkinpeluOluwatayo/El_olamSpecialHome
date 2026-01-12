package enterprise.elroi.dto.requests;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProgressReportRequest {
    private String childId;
    private LocalDate date;
    private String observations;
    private String therapyNotes;
    private String milestones;
    private String createdBy;
}