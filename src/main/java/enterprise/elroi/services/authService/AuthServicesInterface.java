package enterprise.elroi.services.authService;

import enterprise.elroi.dto.requests.UserRequest;
import enterprise.elroi.dto.UserResponse;
import enterprise.elroi.security.UserPrincipal;


public interface AuthServicesInterface {


    UserResponse register(UserRequests request);
    UserResponse login(String email, String password);

    UserResponse adminLogin(String email, String password);

    UserResponse getCurrentUser(String userId);


    void forgotPassword(String email);

    void resetPassword(String token, String newPassword);

    UserPrincipal loadUserById(String userId);

    boolean existsByEmail(String email);
}