package com.opencode.alumxbackend.users.controller;

import com.opencode.alumxbackend.users.dto.DevUserRequest;
import com.opencode.alumxbackend.users.model.User;
import com.opencode.alumxbackend.users.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dev/users")
@RequiredArgsConstructor
public class DevUserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String DUMMY_TOKEN = "alumx-dev-token";

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(DevUserController.class.getName());

    /**
     * Creates a new user in the database.
     * This endpoint is restricted to development/testing environments and requires
     * a valid X-DUMMY-TOKEN header.
     *
     * @param token   The mandatory security token passed in the X-DUMMY-TOKEN
     *                header.
     * @param request The request body containing user details (username, email,
     *                role, etc.).
     * @return ResponseEntity containing the creation status and basic user info.
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestHeader(value = "X-DUMMY-TOKEN", required = false) String token,
            @Valid @RequestBody DevUserRequest request) {

        // 1. Validate Token
        if (token == null || !token.equals(DUMMY_TOKEN)) {
            logger.warning("Unathorized access attempt to Dev API. Missing or invalid token.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Invalid or missing X-DUMMY-TOKEN header"));
        }

        // 2. Validate Uniqueness
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.info("Attempt to create duplicate user with email: " + request.getEmail());
            return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
        }

        // 3. Create User
        logger.info("Creating new user: " + request.getUsername() + " with role: " + request.getRole());
        User user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole().toUpperCase())
                .build();

        userRepository.save(user);
        logger.info("User created successfully: " + user.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "User created successfully via DEV API",
                "userId", user.getId(),
                "role", user.getRole()));
    }
}
