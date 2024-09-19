package com.goomer.api.model.dto;

import com.goomer.api.model.Restaurant;

public record RestaurantDTO(
        Long id,
        String name,
        String imageUrl,
        String description,
        String openingHours
) {
    public static RestaurantDTO restaurantToDTO(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getImageUrl(),
                restaurant.getDescription(),
                restaurant.getOpeningHours()
        );
    }
}