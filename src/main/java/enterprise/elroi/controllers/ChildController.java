package enterprise.elroi.controllers;

import enterprise.elroi.dto.requests.ChildRequest;
import enterprise.elroi.dto.response.ChildResponse;
import enterprise.elroi.services.childService.ChildServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/el_olam/children")
@CrossOrigin(origins = "http://localhost:5174")
public class ChildController {

    private final ChildServiceInterface childService;

    @Autowired
    public ChildController(ChildServiceInterface childService) {
        this.childService = childService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<ChildResponse> addChild(@Valid @RequestBody ChildRequest request) {
        ChildResponse response = childService.addChild(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChildResponse>> getAllChildren() {
        List<ChildResponse> children = childService.getAllChildren();
        return ResponseEntity.ok(children);
    }

    @GetMapping("/{childId}")
    public ResponseEntity<ChildResponse> getChildById(@PathVariable("childId") String childId) {
        ChildResponse response = childService.getChildById(childId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{childId}")
    public ResponseEntity<ChildResponse> updateChild(
            @PathVariable("childId") String childId,
            @Valid @RequestBody ChildRequest request) {
        ChildResponse response = childService.updateChild(childId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove/{childId}")
    public ResponseEntity<String> deleteChild(@PathVariable("childId") String childId) {
        childService.deleteChild(childId);
        return ResponseEntity.ok("Child record deleted successfully");
    }
}