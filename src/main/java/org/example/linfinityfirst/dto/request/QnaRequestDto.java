package org.example.linfinityfirst.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * USER가 Qna 작성시 사용 Dto
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QnaRequestDto {
    private String title;
    private String question;
}