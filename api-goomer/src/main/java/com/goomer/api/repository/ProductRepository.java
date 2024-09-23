package com.goomer.api.repository;

import com.goomer.api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String name);
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByImageUrl(String imageUrl, Pageable pageable);
    Page<Product> findByPrice(BigDecimal price, Pageable pageable);
    Page<Product> findByImageUrlAndPrice(String imageUrl, BigDecimal price, Pageable pageable);
    Page<Product> findByPromotionalDescription(String promotionalDescription, Pageable pageable);
    Page<Product> findByPromotionalPrice(BigDecimal promotionalPrice, Pageable pageable);
    Page<Product> findByPromotionalDescriptionAndPromotionalPrice(String promotionalDescription, BigDecimal promotionalPrice, Pageable pageable);
    Page<Product> findByPromotionalDays(String promotionalDays, Pageable pageable);
    Page<Product> findByPromotionHours(String promotionHours, Pageable pageable);
    Page<Product> findByPromotionalDaysAndPromotionHours(String promotionalDays, String promotionHours, Pageable pageable);
    Page<Product> findByIsOnPromotion(Boolean isOnPromotion, Pageable pageable);
    Page<Product> findByRestaurant_Id(Long restaurantId, Pageable pageable);
}