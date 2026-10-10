package com.logisticsapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDateTime;

// config - HTTP Basic security with two in-memory users (admin, staff)
@Configuration
public class SecurityConfig {

    // passwords are read from application.properties (the old passwords are the default)
    @Value("${app.security.admin-password:admin123}")
    private String adminPassword;

    @Value("${app.security.staff-password:staff123}")
    private String staffPassword;

    // security rules: swagger and health are open, DELETE needs ADMIN, everything else needs login
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/actuator/health")
                        .permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                // shows a clear message when a logged-in user (staff) is not allowed to do something
                .exceptionHandling(ex -> ex.accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write("{\"status\":403,"
                            + "\"message\":\"Access denied - only ADMIN can delete records\","
                            + "\"time\":\"" + LocalDateTime.now() + "\"}");
                }));
        return http.build();
    }

    // BCrypt encoder used to hash passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // two in-memory users: admin and staff (passwords come from application.properties)
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("admin").password(encoder.encode(adminPassword)).roles("ADMIN").build();
        UserDetails staff = User.withUsername("staff").password(encoder.encode(staffPassword)).roles("STAFF").build();
        return new InMemoryUserDetailsManager(admin, staff);
    }
}