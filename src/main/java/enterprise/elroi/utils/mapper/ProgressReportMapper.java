package enterprise.elroi.utils.mapper;

import enterprise.elroi.data.model.ProgressReport;
import enterprise.elroi.dto.requests.ProgressReportRequest;
import enterprise.elroi.dto.response.ProgressReportResponse;
import org.springframework.stereotype.Component;

@Component
public class ProgressReportMapper {

    public ProgressReport toModel(ProgressReportRequest request) {
        if (request == null) return null;

        ProgressReport report = new ProgressReport();
        report.setChildId(request.getChildId());
        report.setDate(request.getDate());
        report.setObservations(request.getObservations());
        report.setTherapyNotes(request.getTherapyNotes());
        report.setMilestones(request.getMilestones());
        report.setCreatedBy(request.getCreatedBy());
        return report;
    }

    public ProgressReportResponse toResponse(ProgressReport report) {
        if (report == null) return null;

        ProgressReportResponse response = new ProgressReportResponse();
        response.setId(report.getId());
        response.setChildId(report.getChildId());
        response.setDate(report.getDate());
        response.setObservations(report.getObservations());
        response.setTherapyNotes(report.getTherapyNotes());
        response.setMilestones(report.getMilestones());
        response.setCreatedBy(report.getCreatedBy());
        return response;
    }
}