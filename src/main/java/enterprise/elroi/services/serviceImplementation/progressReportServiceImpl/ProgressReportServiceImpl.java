package enterprise.elroi.services.serviceImplementation.progressReportServiceImpl;

import enterprise.elroi.data.model.ProgressReport;
import enterprise.elroi.data.repository.ProgressReportRepository;
import enterprise.elroi.dto.requests.ProgressReportRequest;
import enterprise.elroi.dto.response.ProgressReportResponse;
import enterprise.elroi.exceptions.progressReportException.ProgressReportNotFoundException;
import enterprise.elroi.services.progressReportService.ProgressReportInterface;
import enterprise.elroi.utils.mapper.ProgressReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressReportServiceImpl implements ProgressReportInterface {

    private final ProgressReportRepository repository;
    private final ProgressReportMapper mapper;

    @Autowired
    public ProgressReportServiceImpl(ProgressReportRepository repository, ProgressReportMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProgressReportResponse createReport(ProgressReportRequest request) {
        ProgressReport report = mapper.toModel(request);
        ProgressReport savedReport = repository.save(report);
        return mapper.toResponse(savedReport);
    }

    @Override
    public List<ProgressReportResponse> getReportsByChildId(String childId) {
        return repository.findByChildId(childId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProgressReportResponse getLatestReport(String childId) {
        return repository.findByChildId(childId).stream().max(Comparator.comparing(ProgressReport::getDate)).map(mapper::toResponse).orElseThrow(() -> new ProgressReportNotFoundException("No progress reports found for this child"));
    }
}