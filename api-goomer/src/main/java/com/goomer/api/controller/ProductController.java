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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getAllProducts(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String category,
                                            @RequestParam(required = false) Boolean isOnPromotion,
                                            Pageable pageable) {
        return productService.getAllProducts(name, category, isOnPromotion, pageable).getContent();
    }

    @GetMapping("/restaurant/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getProductsByRestaurant(@PathVariable Long restaurantId, Pageable pageable) {
        return productService.getProductsByRestaurant(restaurantId, pageable).getContent();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
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
