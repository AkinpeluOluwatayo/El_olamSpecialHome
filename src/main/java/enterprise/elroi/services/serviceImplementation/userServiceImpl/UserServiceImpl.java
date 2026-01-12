package enterprise.elroi.services.serviceImplementation.userServiceImpl;

import enterprise.elroi.data.model.User;
import enterprise.elroi.data.repository.UserRepository;
import enterprise.elroi.dto.requests.UserRequest;
import enterprise.elroi.dto.response.UserResponse;
import enterprise.elroi.exceptions.userServiceException.UserNotFoundException;
import enterprise.elroi.services.userService.UserServiceInterface;
import enterprise.elroi.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse createAndLinkParent(UserRequest request, String childId) {
        // Ensure the role is set to PARENT
        request.setRole("PARENT");
        request.setLinkedChildId(childId);

        User user = userMapper.toUser(request);
        // Note: Password hashing should be handled here or in AuthServiceImpl
        // depending on your preference. Assuming AuthServiceImpl handles the "Registration"
        // flow, this method focuses on the Linkage.

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse findUserByEmail(String email) {
        User user = (User) userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getLinkedParentData(String childId) {
        // Find the user who has this specific childId in their list
        return userRepository.findAll().stream()
                .filter(user -> user.getChildrenIds().contains(childId))
                .findFirst()
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new RuntimeException("No parent linked to this child"));
    }
}