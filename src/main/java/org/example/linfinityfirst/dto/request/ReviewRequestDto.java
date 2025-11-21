package org.example.linfinityfirst.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * USER가 리뷰 작성시 사용 Dto
 */


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    private Long productId;
    private String content;
}