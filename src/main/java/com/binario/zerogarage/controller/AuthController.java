package com.binario.zerogarage.controller;

import com.binario.zerogarage.dto.RegisterRequest;
import com.binario.zerogarage.dto.SocialLoginRequest;
import com.binario.zerogarage.entity.User;
import com.binario.zerogarage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    /**
     * Endpoint for standard Email/Password signups.
     * URL: POST http://localhost:8080/api/auth/register
     * @param request: Record to dictate the properties required for the request.
     * @return ResponseEntity ok/error
     */
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            User newUser = userService.registerNewUser(request.email(), request.password());
            return ResponseEntity.ok("User registered successfully with Id: " + newUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint for NextAuth Google/Apple logins.
     * URL: POST http://localhost:8080/api/auth/social-login
     * @param request: Record to dictate the properties required for the request.
     * @return ResponseEntity ok/error
     */
    @PostMapping("/social-login")
    public ResponseEntity<String> socialLogin(@RequestBody SocialLoginRequest request) {
        User user = userService.processSocialLogin(
                request.provider(),
                request.providerId(),
                request.email()
        );

        // In phase 2, we will generate and return a JWT here.
        // For now, we return a success message to verify the database linking works.
        return ResponseEntity.ok("Social Login successful for user: " + user.getEmail());
    }

}
