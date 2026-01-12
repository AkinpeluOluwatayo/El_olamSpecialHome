package enterprise.elroi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/el_olam/auth/login/**").permitAll()
                        .requestMatchers("/el_olam/auth/login").permitAll()

                        // 2. Account Creation is RESTRICTED to CEO and Director
                        .requestMatchers("/el_olam/auth/onboard-parent").hasAnyAuthority("CEO", "DIRECTOR")

                        // 3. Management
                        .requestMatchers("/el_olam/children/**").hasAnyAuthority("CEO", "DIRECTOR")
                        .requestMatchers("/el_olam/inventory/**").hasAnyAuthority("CEO", "DIRECTOR")
                        .requestMatchers("/el_olam/reports/create").hasAnyAuthority("CEO", "DIRECTOR")

                        // 4. Parental/Shared Access
                        .requestMatchers("/el_olam/media/child/**").hasAnyAuthority("USER", "CEO", "DIRECTOR")
                        .requestMatchers("/el_olam/reports/child/**").hasAnyAuthority("USER", "CEO", "DIRECTOR")

                        // 5. CEO specific
                        .requestMatchers("/el_olam/users/all").hasAuthority("CEO")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}