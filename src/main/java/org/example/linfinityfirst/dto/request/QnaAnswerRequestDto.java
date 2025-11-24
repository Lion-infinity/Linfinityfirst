package org.example.linfinityfirst.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * USER가 작성한 qna에 답변 등록시 사용 Dto
 */

@Getter
@NoArgsConstructor
public class QnaAnswerRequestDto {
    private String answer;
}