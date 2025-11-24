package org.example.linfinityfirst.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 상품 등록시 사용 DTO
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private String name;
    private String imageUrl;
    private String description;
    private Integer stock;
}