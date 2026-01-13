package enterprise.elroi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/el_olam/auth/login/**").permitAll()

                        // Account creation restricted to Management
                        .requestMatchers("/el_olam/auth/onboard-parent").hasAnyRole("CEO", "DIRECTOR")

                        // Management operations
                        .requestMatchers("/el_olam/children/**", "/el_olam/inventory/**").hasAnyRole("CEO", "DIRECTOR")

                        // Parental Access (Service layer filters specific child data)
                        .requestMatchers("/el_olam/reports/child/**").hasAnyRole("USER", "PARENT", "CEO", "DIRECTOR")

                        // CEO Oversight
                        .requestMatchers("/el_olam/users/all").hasRole("CEO")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
}