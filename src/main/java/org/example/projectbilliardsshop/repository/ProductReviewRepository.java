package org.example.projectbilliardsshop.repository;

import org.example.projectbilliardsshop.model.Product;
import org.example.projectbilliardsshop.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
    List<ProductReview> findByProduct(Product product);
}
