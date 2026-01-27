package enterprise.elroi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Added
import org.springframework.security.crypto.password.PasswordEncoder;     // Added
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/el_olam/auth/login", "/el_olam/auth/login/**").permitAll()
                        .requestMatchers("/el_olam/children/**").hasAnyAuthority("ROLE_CEO", "ROLE_DIRECTOR", "ROLE_PARENT")
                        .requestMatchers("/el_olam/media/child/**").hasAnyAuthority("ROLE_CEO", "ROLE_DIRECTOR", "ROLE_PARENT")
                        .requestMatchers("/el_olam/media/upload").hasAnyAuthority("ROLE_CEO", "ROLE_DIRECTOR")
                        .requestMatchers("/el_olam/media/delete/**").hasAnyAuthority("ROLE_CEO", "ROLE_DIRECTOR")
                        .requestMatchers("/el_olam/reports/create").hasAnyAuthority("ROLE_CEO", "ROLE_DIRECTOR")
                        .requestMatchers("/el_olam/reports/child/**").hasAnyAuthority("ROLE_PARENT", "ROLE_CEO", "ROLE_DIRECTOR")
                        .requestMatchers("/el_olam/auth/onboard-parent/**").hasAnyAuthority("ROLE_CEO", "ROLE_DIRECTOR")
                        .requestMatchers("/el_olam/inventory/**").hasAnyAuthority("ROLE_CEO", "ROLE_DIRECTOR")
                        .requestMatchers("/el_olam/users/all").hasAnyAuthority("ROLE_CEO")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:5174"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
}