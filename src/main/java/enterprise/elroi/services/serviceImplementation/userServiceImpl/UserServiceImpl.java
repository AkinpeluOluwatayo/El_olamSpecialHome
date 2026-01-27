package enterprise.elroi.services.serviceImplementation;

import java.util.ArrayList;
import java.util.List;
import at.favre.lib.crypto.bcrypt.BCrypt;
import enterprise.elroi.data.model.User;
import enterprise.elroi.data.repository.UserRepository;
import enterprise.elroi.dto.requests.UserRequest;
import enterprise.elroi.dto.response.UserResponse;
import enterprise.elroi.exceptions.userServiceException.UserNotFoundException;
import enterprise.elroi.services.userService.UserServiceInterface;
import enterprise.elroi.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

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
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT,
                    "A parent with email " + request.getEmail() + " is already registered."
            );
        }

        try {
            User user = userMapper.toUser(request);
            user.setRole("ROLE_PARENT");

            List<String> children = new ArrayList<>();
            children.add(childId);
            user.setChildrenIds(children);

            String firstName = request.getName().split(" ")[0].toLowerCase();
            int randomDigits = (int)(Math.random() * 9000) + 1000;
            String readablePassword = firstName + "@" + randomDigits;


            String hashedPassword = BCrypt.withDefaults().hashToString(12, readablePassword.toCharArray());
            user.setPassword(hashedPassword);

            User savedUser = userRepository.save(user);
            UserResponse response = userMapper.toUserResponse(savedUser);
            response.setPassword(readablePassword);

            return response;

        } catch (IncorrectResultSizeDataAccessException e) {
            throw new RuntimeException("System Error: Multiple accounts found for " + request.getEmail() + ". Please clean the database.");
        }
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse findUserByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
            return userMapper.toUserResponse(user);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new RuntimeException("Login Error: Multiple users found with email " + email + ". Manual cleanup required.");
        }
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