package enterprise.elroi.utils.mapper;

import enterprise.elroi.data.model.User;
import enterprise.elroi.dto.requests.UserRequest;
import enterprise.elroi.dto.response.UserResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class UserMapper {

    public User toUser(UserRequest request) {
        if (request == null) return null;

        User user = new User();
        // FIXED: Added name mapping
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Initialize lists from model
        user.setChildrenIds(new ArrayList<>());
        user.setProgressReportIds(new ArrayList<>());

        // If a childId is provided during creation, link it immediately
        if (request.getLinkedChildId() != null && !request.getLinkedChildId().isEmpty()) {
            user.getChildrenIds().add(request.getLinkedChildId());
        }

        return user;
    }

    public UserResponse toUserResponse(User user) {
        if (user == null) return null;

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setPhoneNumber(user.getPhoneNumber());

        if (user.getChildrenIds() != null && !user.getChildrenIds().isEmpty()) {
            response.setLinkedChildId(user.getChildrenIds().get(0));
        }

        return response;
    }
}