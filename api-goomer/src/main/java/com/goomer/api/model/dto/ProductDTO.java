package com.goomer.api.model.dto;

import com.goomer.api.model.Product;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record ProductDTO(
        UUID id,
        String name,
        String imageUrl,
        BigDecimal price,
        String category,
        String promotionalDescription,
        BigDecimal promotionalPrice,
        String promotionalDays,
        String promotionHours,
        Boolean isOnPromotion,
        Long restaurantId
) {
    public static ProductDTO productToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getPrice(),
                product.getCategory(),
                product.getPromotionalDescription(),
                product.getPromotionalPrice(),
                product.getPromotionalDays(),
                product.getPromotionHours(),
                product.getIsOnPromotion() != null ? product.getIsOnPromotion() : false,
                product.getRestaurant().getId()
        );
    }
}