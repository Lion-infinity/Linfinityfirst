package org.example.linfinityfirst.controller;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.dto.request.ProductRequestDto;
import org.example.linfinityfirst.dto.response.ProductResponseDto;
import org.example.linfinityfirst.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 1. 상품 등록 (SELLER)
    // DTO를 RequestPart로 받는 대신, 개별 필드를 @RequestParam으로 받도록 변경합니다.
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}) // JSON_VALUE 제거
    public ResponseEntity<Long> createProduct(
            // DTO 필드들을 개별적으로 @RequestParam으로 받습니다.
            // name, description, stock, imageUrl은 ProductRequestDto의 필드입니다.
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Integer stock,
            @RequestParam(required = false) String imageUrl, // 이미지 URL은 선택적으로 받습니다.
            @RequestPart(required = false) MultipartFile imageFile,
            Principal principal) throws IOException {

        // 받은 개별 필드들을 ProductRequestDto 객체로 변환하여 Service에 전달합니다.
        ProductRequestDto requestDto = new ProductRequestDto(name, imageUrl, description, stock);

        Long productId = productService.createProduct(requestDto, imageFile, principal.getName());
        return ResponseEntity.ok(productId);
    }

    // 2. 상품 전체 목록 조회 (ALL)
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // 3. 상품 상세 조회 (ALL)
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    // 4. 상품 수정 (SELLER)
    @PutMapping(value = "/{productId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long productId,
            @RequestPart ProductRequestDto requestDto,
            @RequestPart(required = false) MultipartFile imageFile,
            Principal principal) throws IOException {

        productService.updateProduct(productId, requestDto, imageFile, principal.getName());
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId, Principal principal) {
        productService.deleteProduct(productId, principal.getName());
        return ResponseEntity.ok("상품이 삭제되었습니다.");
    }
}