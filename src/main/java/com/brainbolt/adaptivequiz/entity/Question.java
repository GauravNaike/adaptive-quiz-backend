package com.brainbolt.adaptivequiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int difficulty;

    @Column(nullable = false, length = 1000)
    private String prompt;

    @Column(nullable = false)
    private String correctAnswer;
}
