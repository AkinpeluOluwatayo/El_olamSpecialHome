package enterprise.elroi.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProgressReportResponse {
    private String id;
    private String childId;
    private LocalDate date;
    private String observations;
    private String therapyNotes;
    private String milestones;
    private String createdBy;
}