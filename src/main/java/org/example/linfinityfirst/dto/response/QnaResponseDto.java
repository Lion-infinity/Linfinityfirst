package org.example.linfinityfirst.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.linfinityfirst.domain.Qna;
import java.time.LocalDateTime;
/*
 * qna 조회시 사용 Dto
 */

@Getter
@AllArgsConstructor
public class QnaResponseDto {
    private Long qnaId;
    private String title;
    private String username;
    private boolean isAnswered;

    private String question;
    private String answer;

    private LocalDateTime questionCreatedAt;
    private LocalDateTime questionUpdatedAt;
    private LocalDateTime answeredAt;
    private LocalDateTime answerUpdatedAt;

    //엔티티 -> dto 변환메서드
    public static QnaResponseDto from(Qna qna) {
        return new QnaResponseDto(
                qna.getId(),
                qna.getTitle(),
                qna.getUser().getUsername(),
                qna.getAnswer() != null, // isAnswered 계산
                qna.getQuestion(),
                qna.getAnswer(),
                qna.getCreatedAt(),
                qna.getUpdatedAt(),
                qna.getAnsweredAt(),
                qna.getAnswerUpdatedAt()
        );
    }
}