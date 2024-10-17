package com.goomer.api.service;

import com.goomer.api.exceptions.ResourceNotFoundException;
import com.goomer.api.model.Product;
import com.goomer.api.model.Restaurant;
import com.goomer.api.model.dto.ProductDTO;
import com.goomer.api.model.dto.ProductRequestDTO;
import com.goomer.api.repository.ProductRepository;
import com.goomer.api.repository.RestaurantRepository;
import com.goomer.api.specification.ProductSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;

    public ProductService(ProductRepository productRepository, RestaurantRepository restaurantRepository) {
        this.productRepository = productRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductDTO::productToDTO);
    }

    public ProductDTO getProductById(UUID id) {
        Product product = verifyProductIdExists(id);
        return ProductDTO.productToDTO(product);
    }

    public Page<ProductDTO> getProductsByRestaurant(Long restaurantId, Pageable pageable) {
        Restaurant restaurant = verifyRestaurantIdExists(restaurantId);
        Page<Product> products = productRepository.findByRestaurant(restaurant, pageable);
        return products.map(ProductDTO::productToDTO);
    }

    public Page<ProductDTO> getAllProducts(String name, String category, Long restaurantId, Boolean isOnPromotion, Pageable pageable) {
        Specification<Product> specification = ProductSpecification.withFilters(name, category, restaurantId, isOnPromotion);
        Page<Product> products = productRepository.findAll(specification, pageable);
        return products.map(ProductDTO::productToDTO);
    }

    @Transactional
    public ProductDTO createProduct(ProductRequestDTO requestDTO) {
        Restaurant existingRestaurant = verifyRestaurantIdExists(requestDTO.restaurantId());

        Product product = requestDTO.toProductEntity(existingRestaurant);
        Product savedProduct = productRepository.save(product);

        return ProductDTO.productToDTO(savedProduct);
    }

    @Transactional
    public ProductDTO updateProduct(UUID productId, ProductRequestDTO requestDTO) {
        Product existingProduct = verifyProductIdExists(productId);
        Restaurant existingRestaurant = verifyRestaurantIdExists(requestDTO.restaurantId());

        existingProduct.setName(requestDTO.name());
        existingProduct.setImageUrl(requestDTO.imageUrl());
        existingProduct.setPrice(requestDTO.price());
        existingProduct.setCategory(requestDTO.category());
        existingProduct.setPromotionalDescription(requestDTO.promotionalDescription());
        existingProduct.setPromotionalPrice(requestDTO.promotionalPrice());
        existingProduct.setPromotionalDays(requestDTO.promotionalDays());
        existingProduct.setIsOnPromotion(requestDTO.isOnPromotion());
        existingProduct.setRestaurant(existingRestaurant);

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductDTO.productToDTO(updatedProduct);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        verifyProductIdExists(productId);
        productRepository.deleteById(productId);
    }

    public Product verifyProductIdExists(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Restaurant verifyRestaurantIdExists(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }
}
