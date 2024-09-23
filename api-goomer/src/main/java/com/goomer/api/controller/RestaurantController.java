package com.goomer.api.controller;

import com.goomer.api.model.dto.RestaurantDTO;
import com.goomer.api.model.dto.RestaurantRequestDTO;
import com.goomer.api.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantDTO> listAllRestaurants(Pageable pageable) {
        return restaurantService.getAllRestaurants(pageable).getContent();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantDTO findRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/searchByNameOrDescription")
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantDTO> findRestaurantsByNameOrDescription(@RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) String description,
                                                                  Pageable pageable) {
        return restaurantService.getRestaurantByNameOrDescription(name, description, pageable).getContent();
    }

    @GetMapping("/searchByImageOrHours")
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantDTO> findRestaurantsByImageOrOpeningHours(@RequestParam(required = false) String imageUrl,
                                                                     @RequestParam(required = false) String openingHours,
                                                                     Pageable pageable) {
        return restaurantService.getRestaurantByImageUrlOrOpeningHours(imageUrl, openingHours, pageable).getContent();
    }

    @PostMapping
    public ResponseEntity<RestaurantDTO> createRestaurant(@Valid @RequestBody RestaurantRequestDTO requestDTO) {
        RestaurantDTO createdRestaurant = restaurantService.createRestaurant(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantRequestDTO requestDTO) {
        RestaurantDTO updatedRestaurant = restaurantService.updateRestaurant(id, requestDTO);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}