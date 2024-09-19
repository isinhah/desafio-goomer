package com.goomer.api.repository;

import com.goomer.api.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Page<Restaurant> findByNameAndDescription(String name, String description, Pageable pageable);
    Page<Restaurant> findByName(String name, Pageable pageable);
    Page<Restaurant> findByDescription(String description, Pageable pageable);
    Page<Restaurant> findByImageUrlAndOpeningHours(String imageUrl, String openingHours, Pageable pageable);
    Page<Restaurant> findByImageUrl(String imageUrl, Pageable pageable);
    Page<Restaurant> findByOpeningHours(String openingHours, Pageable pageable);
}