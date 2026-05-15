package com.binario.zerogarage.service;


import com.binario.zerogarage.entity.Role;
import com.binario.zerogarage.entity.SocialAccount;
import com.binario.zerogarage.entity.User;
import com.binario.zerogarage.repository.RoleRepository;
import com.binario.zerogarage.repository.SocialAccountRepository;
import com.binario.zerogarage.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SocialAccountRepository socialAccountRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Flow 1. Standard Email/Password Registration.
     *
     * @param email: User email.
     * @param rawPassword : User rawPassword.
     * @return New User Object.
     */
    @Transactional
    public User registerNewUser(String email, String rawPassword) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // 1. Hash the password before it ever touch the database.
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 2. Fetch the default role.
        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        // 3. Build and sace the User.
        User newUser = User.builder()
                .email(email)
                .passwordHash(encodedPassword)
                .isActive(true)
                .build();

        newUser.getRoles().add(customerRole);

        return userRepository.save(newUser);
    }

    /**
     * Flow 2: OAuth2 Social Login (Google/Apple)
     *
     * @param provider Social Provider
     * @param providerId Social Provider ID
     * @param email User email
     * @return New User Object.
     */
    public User processSocialLogin(String provider, String providerId, String email) {
        // 1. Check if the exact Google/Apple account has logged in before.
        Optional<SocialAccount> existingSocialAccount = socialAccountRepository.findByProviderAndProviderId(provider, providerId);

        if (existingSocialAccount.isPresent()) {
            return existingSocialAccount.get().getUser(); // Welcome back!
        }

        // 2. If no social account exists, check if the email belongs to an existent user.
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            // 3. If the email is also brand new, create a completely new User-
            Role customerRole = roleRepository.findByName("ROLE_CUSTOMER")
                    .orElseThrow(() -> new IllegalStateException("Default role not found"));

            // No PasswordHash here.
            User newUser = User.builder()
                    .email(email)
                    .isActive(true)
                    .build();
            newUser.getRoles().add(customerRole);
            return userRepository.save(newUser);
        });

        //4. Create the social link and tie it to the user.
        SocialAccount newSocialAccount = SocialAccount.builder()
                .provider(provider)
                .providerId(providerId)
                .user(user)
                .build();

        return user;
    }
}
