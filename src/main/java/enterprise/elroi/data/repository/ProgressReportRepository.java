package enterprise.elroi.data.repository;

import enterprise.elroi.data.model.ProgressReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProgressReportRepository extends MongoRepository<ProgressReport, String> {
    void deleteByChildId(String childId);
    List<ProgressReport> findByChildId(String childId);
}