package com.brainbolt.adaptivequiz.repository;

import com.brainbolt.adaptivequiz.entity.UserState;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserStateRepository extends JpaRepository<UserState, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from UserState u where u.userId = :userId")
    Optional<UserState> findByUserIdForUpdate(@Param("userId") Long userId);
}
