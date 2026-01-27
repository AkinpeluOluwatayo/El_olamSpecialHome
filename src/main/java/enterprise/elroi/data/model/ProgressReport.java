package enterprise.elroi.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "progress_reports")
public class ProgressReport {

    @Id
    private String id;

    private String childId;

    private LocalDate date;

    private String observations;

    private String therapyNotes;

    private String milestones;

    private String createdBy;
}