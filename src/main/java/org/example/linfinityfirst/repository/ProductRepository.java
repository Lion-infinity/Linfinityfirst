package org.example.linfinityfirst.repository;

import org.example.linfinityfirst.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //상품 목록 조회
    List<Product> findBySellerId(Long sellerId);
}
