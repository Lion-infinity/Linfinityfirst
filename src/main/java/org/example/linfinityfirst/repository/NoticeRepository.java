package org.example.linfinityfirst.repository;

import org.example.linfinityfirst.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByCreatedAtDesc();//desc 최신날짜
}