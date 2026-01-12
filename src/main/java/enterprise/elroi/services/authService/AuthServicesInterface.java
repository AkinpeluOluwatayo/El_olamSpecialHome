package enterprise.elroi.services.authService;

import enterprise.elroi.dto.requests.UserRequest;
import enterprise.elroi.dto.response.UserResponse;
import enterprise.elroi.security.UserPrincipal;

public interface AuthServicesInterface {
    // This will now be used by Directors to create Parent accounts
    UserResponse register(UserRequest request);

    UserResponse login(String email, String password);
    UserResponse ceoLogin(String email, String password);
    UserResponse directorLogin(String email, String password);
    UserResponse getCurrentUser(String userId);
    UserPrincipal loadUserById(String userId);
    boolean existsByEmail(String email);
}