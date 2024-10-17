package com.goomer.api.model.dto;

import com.goomer.api.model.Product;
import com.goomer.api.model.Restaurant;

import java.math.BigDecimal;

public record ProductRequestDTO(
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
    public Product toProductEntity(Restaurant restaurant) {
        return new Product(
                null,
                this.name(),
                this.imageUrl(),
                this.price(),
                this.category(),
                this.promotionalDescription(),
                this.promotionalPrice(),
                this.promotionalDays(),
                this.promotionHours(),
                this.isOnPromotion() != null ? this.isOnPromotion() : false,
                restaurant
        );
    }
}
