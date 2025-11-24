package org.example.linfinityfirst.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * qna 게시판 엔티티
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 업데이트 기능 - 생성자에 추가 x
@Table(name = "qna")
public class Qna {

    //qna 글 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //qna 작성자 조인컬럼
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //qna 제목
    @Column(nullable = false)
    private String title;

    //qna 내용
    @Column(nullable = false)
    private String question;

    //qna 답변
    @Column
    private String answer;

    // ====================================

    @CreatedDate // JPA가 자동으로 INSERT 시 시간 저장
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // JPA가 질문 내용 변경(UPDATE) 시 자동 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "answered_at") // 답변이 달린 시간
    private LocalDateTime answeredAt;

    @Column(name = "answer_updated_at") // 답변이 수정된 시간
    private LocalDateTime answerUpdatedAt;

    // ======================================

    public Qna(User user, String title, String question) {
        this.user = user;
        this.title = title;
        this.question = question;
    }

    // qna 질문 수정 메서드 (JPA가 updatedAt 자동 갱신)
    public void updateQuestion(String title, String question) {
        this.title = title;
        this.question = question;
    }

    // 관리자가 답변을 등록 or 수정 메서드
    public void updateAnswer(String answer) {
        this.answer = answer;

        if (this.answeredAt == null) {
            // 처음 답변 다는 경우
            this.answeredAt = LocalDateTime.now();
        } else {
            // 이미 답변이 있었는데 수정하는 경우
            this.answerUpdatedAt = LocalDateTime.now();
        }
    }
}