package com.brainbolt.adaptivequiz.service;

import com.brainbolt.adaptivequiz.dto.AnswerResponse;
import com.brainbolt.adaptivequiz.dto.QuestionResponse;
import com.brainbolt.adaptivequiz.entity.*;
import com.brainbolt.adaptivequiz.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;
    private final UserStateRepository userStateRepository;
    private final AnswerLogRepository answerLogRepository;
    private final ScoreService scoreService;
    private final StringRedisTemplate redisTemplate;
    private final LeaderboardService leaderboardService;


    private final Random random = new Random();

    // =========================
    // GET NEXT QUESTION
    // =========================
    public QuestionResponse getNextQuestion(Long userId) {

        UserState state = userStateRepository.findById(userId)
                .orElseGet(() -> createInitialState(userId));

        List<Question> questions =
                questionRepository.findByDifficulty(state.getCurrentDifficulty());

        if (questions.isEmpty()) {
            throw new RuntimeException("No questions found");
        }

        Question question = questions.get(
                new Random().nextInt(questions.size())
        );

        return QuestionResponse.builder()
                .questionId(question.getId())
                .prompt(question.getPrompt())
                .difficulty(state.getCurrentDifficulty())
                .totalScore(state.getTotalScore())
                .streak(state.getCurrentStreak())
                .build();
    }

    // =========================
    // SUBMIT ANSWER
    // =========================
    @Transactional
    public AnswerResponse submitAnswer(Long userId,
                                       Long questionId,
                                       String answer,
                                       String idempotencyKey) {

        if (answerLogRepository.existsByIdempotencyKey(idempotencyKey)) {
            throw new IllegalArgumentException("Duplicate submission detected");
        }

        UserState state = userStateRepository
                .findByUserIdForUpdate(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        boolean correct = question.getCorrectAnswer()
                .equalsIgnoreCase(answer);

        // ===== Update answers count =====
        state.setTotalAnswers(state.getTotalAnswers() + 1);

        if (correct) {
            state.setCorrectAnswers(state.getCorrectAnswers() + 1);
            state.setCurrentStreak(state.getCurrentStreak() + 1);
        } else {
            state.setCurrentStreak(state.getCurrentStreak() - 1);
        }

        // ===== Calculate accuracy =====
        double accuracy = (double) state.getCorrectAnswers()
                / state.getTotalAnswers();

        int difficulty = state.getCurrentDifficulty();

        // ===== Smart Adaptive Difficulty =====
        if (state.getCurrentStreak() >= 2) {
            difficulty = Math.min(5, difficulty + 1);
            state.setCurrentStreak(0);
        }

        if (state.getCurrentStreak() <= -2) {
            difficulty = Math.max(1, difficulty - 1);
            state.setCurrentStreak(0);
        }

        if (accuracy > 0.80) {
            difficulty = Math.min(5, difficulty + 1);
        }

        if (accuracy < 0.40 && state.getTotalAnswers() > 5) {
            difficulty = Math.max(1, difficulty - 1);
        }

        state.setCurrentDifficulty(difficulty);

        state.setTotalDifficulty(
                state.getTotalDifficulty() + difficulty
        );

        // ===== Calculate score AFTER difficulty update =====
        double delta = scoreService.calculateScore(
                difficulty,
                Math.abs(state.getCurrentStreak()),
                state.getTotalAnswers(),
                state.getCorrectAnswers()
        );

        state.setTotalScore(state.getTotalScore() + delta);

        state.setMaxStreak(
                Math.max(state.getMaxStreak(),
                        Math.abs(state.getCurrentStreak()))
        );

        userStateRepository.save(state);

        answerLogRepository.save(
                AnswerLog.builder()
                        .userId(userId)
                        .questionId(questionId)
                        .correct(correct)
                        .idempotencyKey(idempotencyKey)
                        .build()
        );

        leaderboardService.updateScore(userId, state.getTotalScore());

        Long rank = leaderboardService.getRank(userId);

        return AnswerResponse.builder()
                .correct(correct)
                .totalScore(state.getTotalScore())
                .currentDifficulty(state.getCurrentDifficulty())
                .currentStreak(state.getCurrentStreak())
                .rank(rank)
                .build();
    }

    public Map<String, Object> getUserStats(Long userId) {

        UserState state = userStateRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        double accuracy = state.getTotalAnswers() == 0 ? 0 :
                (double) state.getCorrectAnswers() / state.getTotalAnswers();

        double averageDifficulty = state.getTotalAnswers() == 0 ? 0 :
                state.getTotalDifficulty() / state.getTotalAnswers();

        Long rank = leaderboardService.getRank(userId);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalScore", state.getTotalScore());
        stats.put("accuracyPercent", accuracy * 100);
        stats.put("currentDifficulty", state.getCurrentDifficulty());
        stats.put("averageDifficulty", averageDifficulty);
        stats.put("currentStreak", state.getCurrentStreak());
        stats.put("maxStreak", state.getMaxStreak());
        stats.put("totalAnswers", state.getTotalAnswers());
        stats.put("rank", rank);

        return stats;
    }


    // =========================
    // INITIAL USER STATE
    // =========================
    private UserState createInitialState(Long userId) {

        UserState state = UserState.builder()
                .userId(userId)
                .currentDifficulty(1)
                .currentStreak(0)
                .maxStreak(0)
                .totalScore(0)
                .totalAnswers(0)
                .correctAnswers(0)
                .totalDifficulty(0)
                .build();

        return userStateRepository.save(state);
    }

}
