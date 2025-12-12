package org.example.linfinityfirst.repository;

import org.example.linfinityfirst.domain.Notice;
import org.example.linfinityfirst.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByCreatedAtDesc();

    //마이페이지용: 특정 사용자로 공지사항 목록 찾기 (최신순) - 관리자가 본인 글 볼 때 사용
    List<Notice> findAllByUserOrderByCreatedAtDesc(User user);
}