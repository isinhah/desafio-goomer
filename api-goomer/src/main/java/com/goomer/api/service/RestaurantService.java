package com.goomer.api.service;

import com.goomer.api.exceptions.ResourceNotFoundException;
import com.goomer.api.model.Restaurant;
import com.goomer.api.model.dto.RestaurantDTO;
import com.goomer.api.model.dto.RestaurantRequestDTO;
import com.goomer.api.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Page<RestaurantDTO> getAllRestaurants(Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantRepository.findAll(pageable);
        return restaurantPage.map(RestaurantDTO::restaurantToDTO);
    }

    public Restaurant verifyRestaurantIdExists(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }

    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = verifyRestaurantIdExists(id);
        return RestaurantDTO.restaurantToDTO(restaurant);
    }

    public Page<RestaurantDTO> getRestaurantByNameOrDescription(String name, String description, Pageable pageable) {
        Page<Restaurant> restaurants;

        if (name != null && description != null) {
            restaurants = restaurantRepository.findByNameAndDescription(name, description, pageable);
        } else if (name != null) {
            restaurants = restaurantRepository.findByName(name, pageable);
        } else if (description != null) {
            restaurants = restaurantRepository.findByDescription(description, pageable);
        } else {
            throw new IllegalArgumentException("At least one parameter (name or description) must be provided.");
        }

        return restaurants.map(RestaurantDTO::restaurantToDTO);
    }

    public Page<RestaurantDTO> getRestaurantByImageUrlOrOpeningHours(String imageUrl, String openingHours, Pageable pageable) {
        Page<Restaurant> restaurants;

        if (imageUrl != null && openingHours != null) {
            restaurants = restaurantRepository.findByImageUrlAndOpeningHours(imageUrl, openingHours, pageable);
        } else if (imageUrl != null) {
            restaurants = restaurantRepository.findByImageUrl(imageUrl, pageable);
        } else if (openingHours != null) {
            restaurants = restaurantRepository.findByOpeningHours(openingHours, pageable);
        } else {
            throw new IllegalArgumentException("At least one parameter (image url or opening hours) must be provided.");
        }

        return restaurants.map(RestaurantDTO::restaurantToDTO);
    }

    @Transactional
    public RestaurantDTO createRestaurant(RestaurantRequestDTO requestDTO) {
        Restaurant restaurant = requestDTO.toRestaurantEntity();
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantDTO.restaurantToDTO(savedRestaurant);
    }

    @Transactional
    public RestaurantDTO updateRestaurant(Long restaurantId, RestaurantRequestDTO requestDTO) {
        Restaurant existingRestaurant = verifyRestaurantIdExists(restaurantId);

        existingRestaurant.setName(requestDTO.name());
        existingRestaurant.setDescription(requestDTO.description());
        existingRestaurant.setImageUrl(requestDTO.imageUrl());
        existingRestaurant.setOpeningHours(requestDTO.openingHours());

        Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);

        return RestaurantDTO.restaurantToDTO(updatedRestaurant);
    }

    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        verifyRestaurantIdExists(restaurantId);
        restaurantRepository.deleteById(restaurantId);
    }
}