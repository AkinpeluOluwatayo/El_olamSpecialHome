package enterprise.elroi.controllers;

import enterprise.elroi.dto.requests.UserRequest;
import enterprise.elroi.dto.response.UserResponse;
import enterprise.elroi.services.userService.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/el_olam/users")
@CrossOrigin(origins = "http://localhost:5174")
public class UserController {

    private final UserServiceInterface userService;

    @Autowired
    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    // Director's endpoint to create a parent and link them to a child ID
    @PostMapping("/onboard-parent/{childId}")
    public ResponseEntity<UserResponse> onboardParent(@Valid @RequestBody UserRequest request, @PathVariable("childId") String childId) {
        UserResponse response = userService.createAndLinkParent(request, childId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // CEO's view to see everyone in the system
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Find a specific user by their email
    @GetMapping("/search")
    public ResponseEntity<UserResponse> findUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    // Find the parent associated with a specific child
    @GetMapping("/parent-of/{childId}")
    public ResponseEntity<UserResponse> getParentByChild(@PathVariable("childId") String childId) {
        return ResponseEntity.ok(userService.getLinkedParentData(childId));
    }
}