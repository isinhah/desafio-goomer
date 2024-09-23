package com.goomer.api.controller;

import com.goomer.api.model.dto.ProductDTO;
import com.goomer.api.model.dto.ProductRequestDTO;
import com.goomer.api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> listAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable).getContent();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @GetMapping("/searchByName")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO findProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }

    @GetMapping("/searchByCategory")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findProductsByCategory(@RequestParam String category, Pageable pageable) {
        return productService.getProductsByCategory(category, pageable).getContent();
    }

    @GetMapping("/searchByRestaurant")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findProductsByRestaurant(@RequestParam Long restaurantId, Pageable pageable) {
        return productService.getProductsByRestaurant(restaurantId, pageable).getContent();
    }

    @GetMapping("/searchByPromotion")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findProductsByPromotionAvailability(@RequestParam Boolean promotion, Pageable pageable) {
        return productService.getProductsByPromotionAvailability(promotion, pageable).getContent();
    }

    @GetMapping("/searchByImageOrPrice")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findProductsByImageOrPrice(@RequestParam(required = false) String imageUrl,
                                                       @RequestParam(required = false) BigDecimal price,
                                                       Pageable pageable) {
        return productService.getProductsByImageUrlAndPrice(imageUrl, price, pageable).getContent();
    }

    @GetMapping("/searchByPromotionalDescriptionOrPromotionalPrice")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findProductsByPromotionalDescriptionOrPromotionalPrice(
            @RequestParam(required = false) String promotionalDescription,
            @RequestParam(required = false) BigDecimal promotionalPrice,
            Pageable pageable) {
        return productService.getProductsByPromotionalDescriptionAndPromotionalPrice(promotionalDescription, promotionalPrice, pageable).getContent();
    }

    @GetMapping("/searchByPromotionalDaysOrPromotionHours")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findProductsByPromotionalDaysOrPromotionHours(
            @RequestParam(required = false) String promotionalDays,
            @RequestParam(required = false) String promotionHours,
            Pageable pageable) {
        return productService.getProductsByPromotionalDaysAndPromotionHours(promotionalDays, promotionHours, pageable).getContent();
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductDTO createdProduct = productService.createProduct(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductDTO existingProduct = productService.updateProduct(id, requestDTO);
        return ResponseEntity.ok(existingProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
