package com.ansysan.timetracker.controller;

import com.ansysan.timetracker.dto.JwtResponse;
import com.ansysan.timetracker.dto.request.SignInRequest;
import com.ansysan.timetracker.dto.request.SignUpRequest;
import com.ansysan.timetracker.dto.response.ApiResponse;
import com.ansysan.timetracker.entity.User;
import com.ansysan.timetracker.exception.EmailNotFoundException;
import com.ansysan.timetracker.exception.UsernameAlreadyExistsException;
import com.ansysan.timetracker.service.UserService;
import com.ansysan.timetracker.util.JwtUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    /**
     * Авторизация пользователя.
     */
    @PostMapping("/sign")
    public ResponseEntity<?> sign(@Valid @RequestBody SignInRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    /**
     * Регистрация пользователя.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) throws BadRequestException {
        log.info("creating user {}", signUpRequest.getUsername());

        User user = User
                .builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .build();

        try {
            userService.create(user);
        } catch (UsernameAlreadyExistsException | EmailNotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true,"User registered successfully"));
    }
}