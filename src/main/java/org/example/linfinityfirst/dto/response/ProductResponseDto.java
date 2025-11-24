package org.example.linfinityfirst.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.linfinityfirst.domain.Product;

/*
 * 상품 조회시 사용 Dto
 */

@Getter
@AllArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private String name;
    private String imageUrl;
    private String description;
    private Integer stock;
    private String sellerName;

    //엔티티 -> dto 변환메서드
    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getDescription(),
                product.getStock(),
                product.getSeller().getUsername()
        );
    }
}