package org.example.linfinityfirst.repository;

import org.example.linfinityfirst.domain.Qna;
import org.example.linfinityfirst.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findByUserId(Long userId);
    List<Qna> findByAnsweredAtIsNull();
    //마이페이지용: 특정 사용자가 쓴 글을 최신순으로 조회
    //
    List<Qna> findAllByUserOrderByCreatedAtDesc(User user);

    //전체 목록 조회 시에도 최신글이 위에 오도록 정렬(미사용) -> QnaService.getAllQna()에서 findAll()대신 사용가능
    List<Qna> findAllByOrderByCreatedAtDesc();
}