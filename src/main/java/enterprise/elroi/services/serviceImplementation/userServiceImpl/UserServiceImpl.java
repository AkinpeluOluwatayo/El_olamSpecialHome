package enterprise.elroi.services.serviceImplementation.userServiceImpl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import enterprise.elroi.data.model.User;
import enterprise.elroi.data.repository.UserRepository;
import enterprise.elroi.dto.requests.UserRequest;
import enterprise.elroi.dto.response.UserResponse;
import enterprise.elroi.exceptions.userServiceException.UserNotFoundException;
import enterprise.elroi.services.userService.UserServiceInterface;
import enterprise.elroi.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        request.setRole("PARENT");
        request.setLinkedChildId(childId);

        User user = userMapper.toUser(request);

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            String hashedPassword = BCrypt.withDefaults().hashToString(12, request.getPassword().toCharArray());
            user.setPassword(hashedPassword);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse findUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getLinkedParentData(String childId) {
        return userRepository.findAll().stream()
                .filter(user -> user.getChildrenIds() != null && user.getChildrenIds().contains(childId))
                .findFirst()
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new RuntimeException("No parent linked to this child: " + childId));
    }
}