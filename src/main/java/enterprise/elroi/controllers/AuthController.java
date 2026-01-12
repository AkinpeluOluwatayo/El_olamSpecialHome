package enterprise.elroi.controllers;

import enterprise.elroi.dto.requests.LoginRequest;
import enterprise.elroi.dto.requests.UserRequest;
import enterprise.elroi.dto.response.UserResponse;
import enterprise.elroi.services.authService.AuthServicesInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/el_olam/auth")
public class AuthController {

    private final AuthServicesInterface authService;

    @Autowired
    public AuthController(AuthServicesInterface authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        UserResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        UserResponse response = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/ceo")
    public ResponseEntity<UserResponse> ceoLogin(@RequestBody LoginRequest request) {
        UserResponse response = authService.ceoLogin(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/director")
    public ResponseEntity<UserResponse> directorLogin(@RequestBody LoginRequest request) {
        UserResponse response = authService.directorLogin(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/admin")
    public ResponseEntity<UserResponse> adminLogin(@RequestBody LoginRequest request) {
        UserResponse response = authService.adminLogin(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current-user/{userId}")
    public ResponseEntity<UserResponse> getCurrentUser(@PathVariable("userId") String userId) {
        UserResponse response = authService.getCurrentUser(userId);
        return ResponseEntity.ok(response);
    }
}