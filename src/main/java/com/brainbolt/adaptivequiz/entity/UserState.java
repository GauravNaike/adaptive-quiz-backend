package com.brainbolt.adaptivequiz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_state")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserState {

    @Id
    private Long userId;

    private int currentDifficulty;
    private int currentStreak;
    private int maxStreak;
    private double totalScore;
    private int totalAnswers;
    private int correctAnswers;
    private double totalDifficulty;

}
