package com.brainbolt.adaptivequiz.controller;

import com.brainbolt.adaptivequiz.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/leaderboard")
@CrossOrigin(origins = "http://localhost:5173")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/top")
    public ResponseEntity<?> topLeaderboard() {
        return ResponseEntity.ok(leaderboardService.getTopLeaderboard());
    }
}
