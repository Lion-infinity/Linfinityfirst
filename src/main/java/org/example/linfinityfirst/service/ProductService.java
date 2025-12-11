package org.example.linfinityfirst.service;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.domain.Product;
import org.example.linfinityfirst.domain.User;
import org.example.linfinityfirst.dto.request.ProductRequestDto;
import org.example.linfinityfirst.dto.response.ProductResponseDto;
import org.example.linfinityfirst.repository.ProductRepository;
import org.example.linfinityfirst.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 상품 등록 (이미지 파일 처리 포함)
    @Transactional
    public Long createProduct(ProductRequestDto requestDto, MultipartFile imageFile, String username) throws IOException {
        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("판매자 정보를 찾을 수 없습니다."));

        String imageUrl = "no_image.png"; // 기본 이미지

        // 파일 업로드 로직 (이미지 처리 교육용)
        if (imageFile != null && !imageFile.isEmpty()) {
            // 1. 파일 저장 위치 설정 (예: C:/linfinity_shop/images/)
            String uploadDir = "C:/linfinity_shop/images/";
            // 2. 파일명 중복 방지 (UUID 사용)
            String originalFilename = imageFile.getOriginalFilename();
            String saveFileName = UUID.randomUUID() + "_" + originalFilename;

            // 3. 파일 저장
            File saveFile = new File(uploadDir + saveFileName);
            // 디렉토리 없으면 생성
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            imageFile.transferTo(saveFile);

            // 4. DB에 저장할 경로 (웹에서 접근할 경로)
            imageUrl = "/images/" + saveFileName;
        } else {
            // 파일이 없으면 DTO에 있는 문자열 URL 사용 (연습용 유연성)
            if(requestDto.getImageUrl() != null && !requestDto.getImageUrl().isEmpty()){
                imageUrl = requestDto.getImageUrl();
            }
        }

        Product product = new Product(
                requestDto.getName(),
                imageUrl,
                requestDto.getDescription(),
                requestDto.getStock(),
                seller
        );

        return productRepository.save(product).getId();
    }

    // 상품 전체조회
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    // 상품 상세조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        return ProductResponseDto.from(product);
    }

    // 상품 수정
    @Transactional
    public void updateProduct(Long productId, ProductRequestDto requestDto, MultipartFile imageFile, String username) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("수정하려는 상품이 존재하지 않습니다."));

        // 1. 권한 확인 (상품 등록자와 현재 수정 요청자가 같은지 확인)
        if (!product.getSeller().getUsername().equals(username)) {
            throw new IllegalArgumentException("상품 수정 권한이 없습니다.");
        }

        // 2. 이미지 파일 처리 (신규 파일이 있으면 업로드 및 URL 업데이트)
        String imageUrl = product.getImageUrl(); // 기존 이미지 URL 유지

        if (imageFile != null && !imageFile.isEmpty()) {
            // 파일 업로드 로직 (createProduct의 로직 재활용)
            String uploadDir = "C:/linfinity_shop/images/";
            String originalFilename = imageFile.getOriginalFilename();
            String saveFileName = UUID.randomUUID() + "_" + originalFilename;

            File saveFile = new File(uploadDir + saveFileName);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            imageFile.transferTo(saveFile);

            imageUrl = "/images/" + saveFileName;
        } else {
            // 파일이 없지만 DTO에 새로운 URL이 있다면 사용
            if(requestDto.getImageUrl() != null && !requestDto.getImageUrl().isEmpty()){
                imageUrl = requestDto.getImageUrl();
            }
        }

        // 3. 상품 정보 업데이트 (Product 엔티티의 update 메서드 호출)
        product.update(
                requestDto.getName(),
                imageUrl,
                requestDto.getDescription(),
                requestDto.getStock()
        );
    }
}