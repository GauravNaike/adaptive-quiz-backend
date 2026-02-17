package com.brainbolt.adaptivequiz.service;

import org.springframework.stereotype.Service;

@Service
public class AdaptiveService {

    public int updateDifficulty(int currentDifficulty, boolean correct, int streak) {

        if (correct && streak >= 2) {
            return Math.min(currentDifficulty + 1, 10);
        }

        if (!correct) {
            return Math.max(currentDifficulty - 1, 1);
        }

        return currentDifficulty;
    }
}
