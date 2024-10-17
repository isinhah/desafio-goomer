package com.goomer.api.specification;

import com.goomer.api.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> withFilters(String name, String category, Boolean isOnPromotion) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (name != null && !name.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (category != null && !category.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), "%" + category.toLowerCase() + "%"));
            }

            if (isOnPromotion != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isOnPromotion"), isOnPromotion));
            }

            return predicate;
        };
    }
}
