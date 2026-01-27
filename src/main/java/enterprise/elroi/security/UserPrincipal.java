package enterprise.elroi.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Getter
public class UserPrincipal implements UserDetails {
    private final String id;
    private final String email;
    private final String role;
    private final String childId;

    public UserPrincipal(String id, String email, String role, String childId) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.childId = childId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String formattedRole = role.toUpperCase().startsWith("ROLE_")
                ? role.toUpperCase()
                : "ROLE_" + role.toUpperCase();

        return Collections.singletonList(new SimpleGrantedAuthority(formattedRole));
    }

    @Override public String getPassword() { return null; }
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}