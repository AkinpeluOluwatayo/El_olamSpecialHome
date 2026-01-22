package enterprise.elroi.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;
    private String linkedChildId;
    private String token;
}