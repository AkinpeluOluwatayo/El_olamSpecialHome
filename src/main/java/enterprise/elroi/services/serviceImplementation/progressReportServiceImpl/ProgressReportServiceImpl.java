package enterprise.elroi.services.serviceImplementation.progressReportServiceImpl;

import enterprise.elroi.data.model.ProgressReport;
import enterprise.elroi.data.model.User;
import enterprise.elroi.data.repository.ProgressReportRepository;
import enterprise.elroi.data.repository.UserRepository;
import enterprise.elroi.dto.requests.ProgressReportRequest;
import enterprise.elroi.dto.response.ProgressReportResponse;
import enterprise.elroi.exceptions.progressReportException.ProgressReportNotFoundException;
import enterprise.elroi.security.UserPrincipal;
import enterprise.elroi.services.progressReportService.ProgressReportInterface;
import enterprise.elroi.utils.mapper.ProgressReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProgressReportServiceImpl implements ProgressReportInterface {

    private final ProgressReportRepository repository;
    private final UserRepository userRepository;
    private final ProgressReportMapper mapper;

    @Autowired
    public ProgressReportServiceImpl(ProgressReportRepository repository,
                                     UserRepository userRepository,
                                     ProgressReportMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public ProgressReportResponse createReport(ProgressReportRequest request) {
        ProgressReport report = mapper.toModel(request);
        ProgressReport savedReport = repository.save(report);
        return mapper.toResponse(savedReport);
    }

    @Override
    public List<ProgressReportResponse> getReportsByChildId(String childId, UserPrincipal principal) {
        validateAccess(principal, childId);

        return repository.findByChildId(childId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ProgressReportResponse getLatestReport(String childId, UserPrincipal principal) {
        validateAccess(principal, childId);

        return repository.findByChildId(childId).stream()
                .max(Comparator.comparing(ProgressReport::getDate))
                .map(mapper::toResponse)
                .orElseThrow(() -> new ProgressReportNotFoundException("No progress reports found for child: " + childId));
    }

    private void validateAccess(UserPrincipal principal, String childId) {
        String role = principal.getRole();

        if ("CEO".equalsIgnoreCase(role) || "DIRECTOR".equalsIgnoreCase(role)) {
            return;
        }

        User user = userRepository.findById(principal.getId())
                .orElseThrow(() -> new RuntimeException("Logged in user not found in database"));

        if (!user.getChildrenIds().contains(childId)) {
            throw new AccessDeniedException("Access Denied: You are not linked to this child's profile.");
        }
    }
}