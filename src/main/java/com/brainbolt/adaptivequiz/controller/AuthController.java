package com.brainbolt.adaptivequiz.controller;

import com.brainbolt.adaptivequiz.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody Map<String, String> request) {

        String token = authService.signup(
                request.get("username"),
                request.get("password")
        );

        return Map.of("token", token);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        String token = authService.login(
                request.get("username"),
                request.get("password")
        );

        return Map.of("token", token);
    }
}
