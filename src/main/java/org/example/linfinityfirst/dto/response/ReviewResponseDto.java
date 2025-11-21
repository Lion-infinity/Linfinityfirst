package org.example.linfinityfirst.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.linfinityfirst.domain.Review;

/*
 * 리뷰 조회시 사용 Dto
 */

@Getter
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private String productName;
    private String username;
    private String content;


    //엔티티 -> dto 변환메서드
    public static ReviewResponseDto from(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getProduct().getName(),
                review.getUser().getUsername(),
                review.getContent()
        );
    }
}