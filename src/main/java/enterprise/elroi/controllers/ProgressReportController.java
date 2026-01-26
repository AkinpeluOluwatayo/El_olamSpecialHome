package enterprise.elroi.controllers;

import enterprise.elroi.dto.requests.ProgressReportRequest;
import enterprise.elroi.dto.response.ProgressReportResponse;
import enterprise.elroi.security.UserPrincipal;
import enterprise.elroi.services.progressReportService.ProgressReportInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/el_olam/reports")
@CrossOrigin(origins = "http://localhost:5174")
public class ProgressReportController {

    private final ProgressReportInterface reportService;

    @Autowired
    public ProgressReportController(ProgressReportInterface reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProgressReportResponse> createReport(@Valid @RequestBody ProgressReportRequest request) {
        ProgressReportResponse response = reportService.createReport(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/child/{childId}")
    public ResponseEntity<List<ProgressReportResponse>> getReportsByChildId(@PathVariable("childId") String childId, @AuthenticationPrincipal UserPrincipal principal) {

        List<ProgressReportResponse> reports = reportService.getReportsByChildId(childId, principal);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/child/{childId}/latest")
    public ResponseEntity<ProgressReportResponse> getLatestReport(@PathVariable("childId") String childId, @AuthenticationPrincipal UserPrincipal principal) {

        ProgressReportResponse response = reportService.getLatestReport(childId, principal);
        return ResponseEntity.ok(response);
    }
}