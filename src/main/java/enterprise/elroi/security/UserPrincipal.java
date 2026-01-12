package enterprise.elroi.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class UserPrincipal implements UserDetails {

    private final String id;
    private final String email;
    private final String role; // Changed from roles to role to match your User model

    public UserPrincipal(String id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role != null && !role.isEmpty()) {
            // Standardizing for Spring Security checks
            String roleWithPrefix = role.toUpperCase().startsWith("ROLE_") ?
                    role.toUpperCase() : "ROLE_" + role.toUpperCase();
            return Collections.singletonList(new SimpleGrantedAuthority(roleWithPrefix));
        }
        return Collections.emptyList();
    }

    // This returns the clean role name (e.g., "CEO", "DIRECTOR")
    // used by your AuthServiceImpl logic
    public String getRole() {
        return role;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}