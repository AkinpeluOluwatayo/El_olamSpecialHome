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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthServicesInterface {

    private final UserRepository userRepository;
    private final AuthMapper mapper;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepository userRepository, AuthMapper mapper, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserResponse register(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistException("User already exists with email: " + request.getEmail());
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
                .orElseThrow(() -> new UserLoginNotFoundException("Account not found"));

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if (!result.verified) {
            throw new InvalidPasswordException("Invalid credentials");
        }

        // Explicitly get childId for Parent role
        List<String> childrenIds = user.getChildrenIds();
        String childId = (childrenIds != null && !childrenIds.isEmpty()) ? childrenIds.get(0) : null;

        UserPrincipal principal = new UserPrincipal(user.getId(), user.getEmail(), user.getRole(), childId);
        String token = jwtUtils.generateJwtToken(principal);

        UserResponse response = mapper.toUserResponse(user);
        response.setToken(token);
        return response;
    }

    @Override
    public UserPrincipal loadUserById(String userId) {
        return userRepository.findById(userId)
                .map(u -> {
                    List<String> childrenIds = u.getChildrenIds();
                    String childId = (childrenIds != null && !childrenIds.isEmpty()) ? childrenIds.get(0) : null;
                    return new UserPrincipal(u.getId(), u.getEmail(), u.getRole(), childId);
                })
                .orElseThrow(() -> new UserCurrentLoginNotFoundException("User session invalid"));
    }

    @Override
    public UserResponse ceoLogin(String email, String password) {
        UserResponse res = login(email, password);
        if (!"CEO".equalsIgnoreCase(res.getRole())) throw new UserIsNotAnAdminException("CEO Access Required");
        return res;
    }

    @Override
    public UserResponse directorLogin(String email, String password) {
        UserResponse res = login(email, password);
        if (!"DIRECTOR".equalsIgnoreCase(res.getRole())) throw new UserIsNotAnAdminException("Director Access Required");
        return res;
    }

    @Override public UserResponse getCurrentUser(String userId) {
        return userRepository.findById(userId).map(mapper::toUserResponse).orElseThrow();
    }

    @Override public boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }
}