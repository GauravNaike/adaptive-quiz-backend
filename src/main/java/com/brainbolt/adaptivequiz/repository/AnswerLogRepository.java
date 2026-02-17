package com.brainbolt.adaptivequiz.repository;

import com.brainbolt.adaptivequiz.entity.AnswerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerLogRepository extends JpaRepository<AnswerLog, Long> {

    boolean existsByIdempotencyKey(String idempotencyKey);
}
