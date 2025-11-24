package org.example.linfinityfirst.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * 리뷰 게시판 엔티티
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
public class Review {

    //리뷰 글 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //리뷰 작성자 조인컬럼
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //상품 번호 조인컬럼
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    //리뷰 내용
    @Column(nullable = false)
    private String content;

    public Review(User user, Product product, String content) {
        this.user = user;
        this.product = product;
        this.content = content;
    }
}