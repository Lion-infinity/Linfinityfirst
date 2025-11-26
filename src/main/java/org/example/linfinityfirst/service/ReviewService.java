package org.example.linfinityfirst.service;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.domain.Product;
import org.example.linfinityfirst.domain.Review;
import org.example.linfinityfirst.domain.User;
import org.example.linfinityfirst.dto.request.ReviewRequestDto;
import org.example.linfinityfirst.dto.response.ReviewResponseDto;
import org.example.linfinityfirst.repository.ProductRepository;
import org.example.linfinityfirst.repository.ReviewRepository;
import org.example.linfinityfirst.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    //리뷰 등록-작성자랑 상품 확인 필요
    @Transactional
    public Long createReview(ReviewRequestDto dto, String username) {
        //작성자확인
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        //상품확인
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        //리뷰
        Review review = new Review(user, product, dto.getContent());
        return reviewRepository.save(review).getId();
    }

    //상품 리뷰목록조회
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(ReviewResponseDto::from)
                .collect(Collectors.toList());
    }

    //유저작성리뷰조회
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getMyReviews(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        return reviewRepository.findByUserId(user.getId()).stream()
                .map(ReviewResponseDto::from)
                .collect(Collectors.toList());
    }
}