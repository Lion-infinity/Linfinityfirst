package org.example.linfinityfirst.repository;

import org.example.linfinityfirst.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findByUserId(Long userId);
    List<Qna> findByAnsweredAtIsNull();
}