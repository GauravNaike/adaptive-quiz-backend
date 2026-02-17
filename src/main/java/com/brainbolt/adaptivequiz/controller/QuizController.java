package com.brainbolt.adaptivequiz.controller;

import com.brainbolt.adaptivequiz.dto.AnswerRequest;
import com.brainbolt.adaptivequiz.dto.AnswerResponse;
import com.brainbolt.adaptivequiz.dto.QuestionResponse;
import com.brainbolt.adaptivequiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    //get next question
    @GetMapping("/next")
    public QuestionResponse getNextQuestion(@RequestParam Long userId) {

        return quizService.getNextQuestion(userId);
    }

    //submit answer
    @PostMapping("/answer")
    public AnswerResponse submitAnswer(
            @RequestBody AnswerRequest request) {

        return quizService.submitAnswer(
                request.getUserId(),
                request.getQuestionId(),
                request.getAnswer(),
                request.getIdempotencyKey()
        );
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats(@RequestParam Long userId) {
        return ResponseEntity.ok(quizService.getUserStats(userId));
    }
}
