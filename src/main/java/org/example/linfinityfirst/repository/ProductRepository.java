package org.example.linfinityfirst.repository;

import org.example.linfinityfirst.domain.Product;
import org.example.linfinityfirst.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySellerId(Long sellerId);
    List<Product> findAllBySellerOrderByIdDesc(User seller);
}
