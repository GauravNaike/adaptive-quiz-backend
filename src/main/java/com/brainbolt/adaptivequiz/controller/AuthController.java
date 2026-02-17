package com.brainbolt.adaptivequiz.controller;

import com.brainbolt.adaptivequiz.dto.AuthRequest;
import com.brainbolt.adaptivequiz.dto.AuthResponse;
import com.brainbolt.adaptivequiz.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody AuthRequest request) {

        String token = authService.signup(
                request.getUsername(),
                request.getPassword()
        );

        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        String token = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        return new AuthResponse(token);
    }
}
