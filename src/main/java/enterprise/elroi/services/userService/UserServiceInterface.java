package enterprise.elroi.services.userService;

import enterprise.elroi.dto.requests.UserRequest;
import enterprise.elroi.dto.response.UserResponse;
import java.util.List;

public interface UserServiceInterface {
    UserResponse createAndLinkParent(UserRequest request, String childId);
    List<UserResponse> getAllUsers();
    UserResponse findUserByEmail(String email);
    UserResponse getLinkedParentData(String childId);
}