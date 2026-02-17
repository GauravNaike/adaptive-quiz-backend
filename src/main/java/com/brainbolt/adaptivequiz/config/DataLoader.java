package com.brainbolt.adaptivequiz.config;

import com.brainbolt.adaptivequiz.entity.Question;
import com.brainbolt.adaptivequiz.repository.QuestionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final QuestionRepository questionRepository;

    @PostConstruct
    public void loadData() {

        if (questionRepository.count() > 0) return;

        questionRepository.save(
                Question.builder()
                        .difficulty(1)
                        .prompt("2 + 2 = ?")
                        .correctAnswer("4")
                        .build()
        );

        questionRepository.save(
                Question.builder()
                        .difficulty(2)
                        .prompt("5 * 3 = ?")
                        .correctAnswer("15")
                        .build()
        );

        questionRepository.save(
                Question.builder()
                        .difficulty(3)
                        .prompt("Square root of 81?")
                        .correctAnswer("9")
                        .build()
        );
    }
}
