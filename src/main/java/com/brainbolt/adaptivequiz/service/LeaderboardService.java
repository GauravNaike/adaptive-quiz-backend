package com.brainbolt.adaptivequiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final StringRedisTemplate redisTemplate;

    private static final String SCORE_BOARD = "leaderboard:score";
    private static final String STREAK_BOARD = "leaderboard:streak";

    public void updateScore(Long userId, double score) {
        redisTemplate.opsForZSet()
                .add(SCORE_BOARD, userId.toString(), score);
    }

    public void updateStreak(Long userId, int streak) {
        redisTemplate.opsForZSet()
                .add(STREAK_BOARD, userId.toString(), streak);
    }

    public Long getRank(Long userId) {
        Long rank = redisTemplate.opsForZSet()
                .reverseRank(SCORE_BOARD, userId.toString());

        return rank != null ? rank + 1 : null; // rank starts from 0
    }

    public List<Map<String, Object>> getTopLeaderboard() {

        Set<ZSetOperations.TypedTuple<String>> topUsers =
                redisTemplate.opsForZSet()
                        .reverseRangeWithScores(SCORE_BOARD, 0, 9);

        List<Map<String, Object>> result = new ArrayList<>();
        int rank = 1;

        if (topUsers != null) {
            for (ZSetOperations.TypedTuple<String> entry : topUsers) {
                Map<String, Object> user = new HashMap<>();
                user.put("rank", rank++);
                user.put("userId", entry.getValue());
                user.put("score", entry.getScore());
                result.add(user);
            }
        }

        return result;
    }

}
