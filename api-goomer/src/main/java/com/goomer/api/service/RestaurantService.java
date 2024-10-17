package com.goomer.api.service;

import com.goomer.api.exceptions.ResourceNotFoundException;
import com.goomer.api.model.Restaurant;
import com.goomer.api.model.dto.RestaurantDTO;
import com.goomer.api.model.dto.RestaurantRequestDTO;
import com.goomer.api.repository.RestaurantRepository;
import com.goomer.api.specification.RestaurantSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Page<RestaurantDTO> getAllRestaurants(String name, String description, Pageable pageable) {
        Specification<Restaurant> specification = RestaurantSpecification.withFilters(name, description);
        Page<Restaurant> restaurants = restaurantRepository.findAll(specification, pageable);
        return restaurants.map(RestaurantDTO::restaurantToDTO);
    }

    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = verifyRestaurantIdExists(id);
        return RestaurantDTO.restaurantToDTO(restaurant);
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

    public Restaurant verifyRestaurantIdExists(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }
}