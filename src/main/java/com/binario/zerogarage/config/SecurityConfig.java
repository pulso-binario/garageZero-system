package com.binario.zerogarage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    /**
     * Password Hashing Bean
     * Provides the BCrypt algorithm to the UserService.
     */
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF (Cross-Site Request Forgery)
                // Why? Because our frontend (Next.js) and backend are decoupled.
                // We are building a stateless REST API, so CSRF tokens are unnecessary.
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Enforce Stateless Sessions
                // Why? Spring usually saves login states in server memory. We are turning that off.
                // NextAuth will handle the session cookie on the frontend, and Spring will just validate JWTs.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Define the Route Permissions
                .authorizeHttpRequests(auth -> auth
                        // Open the door for NextAuth: Anyone can attempt to log in or register
                        .requestMatchers("/api/auth/**").permitAll()

                        // Close the door for everything else: All other routes require a valid JWT
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
