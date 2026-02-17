package com.brainbolt.adaptivequiz.controller;

import com.brainbolt.adaptivequiz.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/top")
    public ResponseEntity<?> topLeaderboard() {
        return ResponseEntity.ok(leaderboardService.getTopLeaderboard());
    }
}
