package com.goomer.api.service;

import com.goomer.api.exceptions.ResourceNotFoundException;
import com.goomer.api.model.Product;
import com.goomer.api.model.Restaurant;
import com.goomer.api.model.dto.ProductDTO;
import com.goomer.api.model.dto.ProductRequestDTO;
import com.goomer.api.repository.ProductRepository;
import com.goomer.api.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductDTO::productToDTO);
    }

    public Product verifyProductIdExists(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public ProductDTO getProductById(UUID id) {
        Product product = verifyProductIdExists(id);
        return ProductDTO.productToDTO(product);
    }

    public ProductDTO getProductByName(String name) {
        return productRepository.findByName(name)
                .map(ProductDTO::productToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with name: " + name));
    }

    public Page<ProductDTO> getProductsByCategory(String category, Pageable pageable) {
        Page<Product> products = productRepository.findByCategory(category, pageable);
        return products.map(ProductDTO::productToDTO);
    }

    public Page<ProductDTO> getProductsByRestaurant(Long restaurantId, Pageable pageable) {
        Page<Product> products = productRepository.findByRestaurant_Id(restaurantId, pageable);
        return products.map(ProductDTO::productToDTO);
    }

    public Page<ProductDTO> getProductsByPromotionAvailability(Boolean isOnPromotion, Pageable pageable) {
        Page<Product> products = productRepository.findByIsOnPromotion(isOnPromotion, pageable);
        return products.map(ProductDTO::productToDTO);
    }

    public Page<ProductDTO> getProductsByImageUrlAndPrice(String imageUrl, BigDecimal price, Pageable pageable) {
        Page<Product> products;

        if (imageUrl != null && price != null) {
            products = productRepository.findByImageUrlAndPrice(imageUrl, price, pageable);
        } else if (imageUrl != null) {
            products = productRepository.findByImageUrl(imageUrl, pageable);
        } else if (price != null) {
            products = productRepository.findByPrice(price, pageable);
        } else {
            throw new IllegalArgumentException("At least one of the parameters (image url or price) must be provided.");
        }

        return products.map(ProductDTO::productToDTO);
    }

    public Page<ProductDTO> getProductsByPromotionalDescriptionAndPromotionalPrice(String promotionalDescription, BigDecimal promotionalPrice, Pageable pageable) {
        Page<Product> products;

        if (promotionalDescription != null && promotionalPrice != null) {
            products = productRepository.findByPromotionalDescriptionAndPromotionalPrice(promotionalDescription, promotionalPrice, pageable);
        } else if (promotionalDescription != null) {
            products = productRepository.findByPromotionalDescription(promotionalDescription, pageable);
        } else if (promotionalPrice != null) {
            products = productRepository.findByPromotionalPrice(promotionalPrice, pageable);
        } else {
            throw new IllegalArgumentException("At least one of the parameters (promotional description or promotional price) must be provided.");
        }

        return products.map(ProductDTO::productToDTO);
    }

    public Page<ProductDTO> getProductsByPromotionalDaysAndPromotionHours(String promotionalDays, String promotionHours, Pageable pageable) {
        Page<Product> products;

        if (promotionalDays != null && promotionHours != null) {
            products = productRepository.findByPromotionalDaysAndPromotionHours(promotionalDays, promotionHours, pageable);
        } else if (promotionalDays != null) {
            products = productRepository.findByPromotionalDays(promotionalDays, pageable);
        } else if (promotionHours != null) {
            products = productRepository.findByPromotionHours(promotionHours, pageable);
        } else {
            throw new IllegalArgumentException("At least one of the parameters (promotional days or promotion hours) must be provided.");
        }

        return products.map(ProductDTO::productToDTO);
    }

    @Transactional
    public ProductDTO createProduct(ProductRequestDTO requestDTO) {
        Restaurant restaurant = restaurantRepository.findById(requestDTO.restaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with this id."));

        Product product = requestDTO.toProductEntity(restaurant);
        Product savedProduct = productRepository.save(product);
        return ProductDTO.productToDTO(savedProduct);
    }

    @Transactional
    public ProductDTO updateProduct(UUID productId, ProductRequestDTO requestDTO) {
        Product existingProduct = verifyProductIdExists(productId);

        existingProduct.setName(requestDTO.name());
        existingProduct.setImageUrl(requestDTO.imageUrl());
        existingProduct.setPrice(requestDTO.price());
        existingProduct.setCategory(requestDTO.category());
        existingProduct.setPromotionalDescription(requestDTO.promotionalDescription());
        existingProduct.setPromotionalPrice(requestDTO.promotionalPrice());
        existingProduct.setPromotionalDays(requestDTO.promotionalDays());
        existingProduct.setIsOnPromotion(requestDTO.isOnPromotion());

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductDTO.productToDTO(updatedProduct);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        verifyProductIdExists(productId);
        productRepository.deleteById(productId);
    }
}
