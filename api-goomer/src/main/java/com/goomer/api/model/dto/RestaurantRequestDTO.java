package com.goomer.api.model.dto;

import com.goomer.api.model.Restaurant;

public record RestaurantRequestDTO(
        String name,
        String imageUrl,
        String description,
        String openingHours
) {
    public Restaurant toRestaurantEntity() {
        return new Restaurant(
                null,
                this.name(),
                this.imageUrl(),
                this.description(),
                this.openingHours()
        );
    }
}