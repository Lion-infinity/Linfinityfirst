package org.example.linfinityfirst.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "notices")
public class Notice {

    //공지사항 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //공지사항 제목
    @Column(nullable = false)
    private String title;

    //공지사항 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private int viewCount;

    public Notice(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.viewCount = 0;
    }
    // 수정 편의 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 조회수 증가 편의 메서드
    public void increaseViewCount() {
        this.viewCount++;
    }
}