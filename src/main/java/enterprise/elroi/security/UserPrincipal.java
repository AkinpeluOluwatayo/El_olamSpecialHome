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
    private final String roles;

    public UserPrincipal(String id, String email, String roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles != null && !roles.isEmpty()) {
            // Spring Security requires "ROLE_" prefix for hasRole() checks
            String roleWithPrefix = roles.toUpperCase().startsWith("ROLE_") ?
                    roles.toUpperCase() : "ROLE_" + roles.toUpperCase();
            return Collections.singletonList(new SimpleGrantedAuthority(roleWithPrefix));
        }
        return Collections.emptyList();
    }

    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}