package org.example.linfinityfirst.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
public class Product {

    //상품 구분짓는 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품이름
    @Column(nullable = false)
    private String name;

    //상품 이미지
    @Column(name = "image_url")
    private String imageUrl;

    //상품 설명
    @Column(columnDefinition = "TEXT")
    private String description;

    //재고
    private Integer stock;

    //쇼핑몰 이용자중 판매자에대한 id 조인컬럼
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    public Product(String name, String imageUrl, String description, Integer stock, User seller) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
        this.seller = seller;
    }
}