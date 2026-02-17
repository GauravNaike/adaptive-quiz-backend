package com.brainbolt.adaptivequiz.service;

import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    public double calculateScore(int difficulty,
                                 int streak,
                                 int totalAnswers,
                                 int correctAnswers) {

        if (totalAnswers == 0) return 0;

        double base = difficulty * 10;

        double streakMultiplier = Math.min(1 + (streak * 0.1), 2.0);

        double accuracy = (double) correctAnswers / totalAnswers;

        return base * streakMultiplier * (0.5 + accuracy);
    }
}
