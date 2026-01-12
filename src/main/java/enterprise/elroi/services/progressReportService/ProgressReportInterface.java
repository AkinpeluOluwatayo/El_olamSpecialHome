package enterprise.elroi.services.progressReportService;

import enterprise.elroi.dto.requests.ProgressReportRequest;
import enterprise.elroi.dto.response.ProgressReportResponse;
import java.util.List;

public interface ProgressReportInterface {
    ProgressReportResponse createReport(ProgressReportRequest request);
    List<ProgressReportResponse> getReportsByChildId(String childId);
    ProgressReportResponse getLatestReport(String childId);
}