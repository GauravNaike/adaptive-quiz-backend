package com.brainbolt.adaptivequiz.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionResponse {

    private Long questionId;
    private String prompt;
    private int difficulty;
    private double totalScore;
    private int streak;

}
