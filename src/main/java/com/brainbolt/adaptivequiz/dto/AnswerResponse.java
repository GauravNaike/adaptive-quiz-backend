package com.brainbolt.adaptivequiz.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerResponse {

    private boolean correct;
    private double totalScore;
    private int currentDifficulty;
    private int currentStreak;
    private Long rank;

}
