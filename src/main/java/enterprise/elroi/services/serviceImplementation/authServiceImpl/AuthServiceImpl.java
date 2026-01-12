package enterprise.elroi.services.serviceImplementation.authServiceImpl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import enterprise.elroi.data.model.User;
import enterprise.elroi.data.repository.UserRepository;
import enterprise.elroi.dto.requests.UserRequest;
import enterprise.elroi.dto.response.UserResponse;
import enterprise.elroi.exceptions.authServiceExceptions.*;
import enterprise.elroi.security.JwtUtils;
import enterprise.elroi.security.UserPrincipal;
import enterprise.elroi.services.authService.AuthServicesInterface;
import enterprise.elroi.utils.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthServicesInterface {

    private final UserRepository userRepository;
    private final AuthMapper mapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, AuthMapper mapper, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserResponse register(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistException("User already exists with this email");
        }

        User user = mapper.toUser(request);
        String hashedPassword = BCrypt.withDefaults().hashToString(12, request.getPassword().toCharArray());
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);
        return mapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse login(String email, String password) {
        User user = (User) userRepository.findByEmail(email)
                .orElseThrow(() -> new UserLoginNotFoundException("User not found"));

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if (!result.verified) {
            throw new InvalidPasswordException("Invalid password");
        }

        // Generate Token
        UserPrincipal principal = new UserPrincipal(user.getId(), user.getEmail(), user.getRole());
        String token = jwtUtils.generateJwtToken(principal);

        // Map to response and attach token
        UserResponse response = mapper.toUserResponse(user);
        response.setToken(token);

        return response;
    }

    @Override
    public UserResponse ceoLogin(String email, String password) {
        UserResponse response = login(email, password);
        if (!"CEO".equalsIgnoreCase(response.getRole())) {
            throw new UserIsNotAnAdminException("Access Denied: User is not a CEO");
        }
        return response;
    }

    @Override
    public UserResponse directorLogin(String email, String password) {
        UserResponse response = login(email, password);
        if (!"DIRECTOR".equalsIgnoreCase(response.getRole())) {
            throw new UserIsNotAnAdminException("Access Denied: User is not a Director");
        }
        return response;
    }

    @Override
    public UserResponse adminLogin(String email, String password) {
        UserResponse response = login(email, password);
        String role = response.getRole();

        if (!"CEO".equalsIgnoreCase(role) && !"DIRECTOR".equalsIgnoreCase(role)) {
            throw new UserIsNotAnAdminException("User does not have administrative privileges");
        }
        return response;
    }

    @Override
    public UserResponse getCurrentUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserCurrentLoginNotFoundException("User not found"));
        return mapper.toUserResponse(user);
    }

    @Override
    public UserPrincipal loadUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserCurrentLoginNotFoundException("User not found"));
        return new UserPrincipal(user.getId(), user.getEmail(), user.getRole());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}