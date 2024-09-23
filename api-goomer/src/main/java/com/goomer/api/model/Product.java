package com.goomer.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_PRODUCTS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(nullable = false)
    private String category;

    @Column(name = "promotional_description")
    private String promotionalDescription;
    @Column(name = "promotional_price", precision = 10, scale = 2)
    private BigDecimal promotionalPrice;
    @Column(name = "promotional_days")
    private String promotionalDays;
    @Column(name = "promotion_hours")
    private String promotionHours;
    @Column(name = "is_on_promotion")
    private Boolean isOnPromotion;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}