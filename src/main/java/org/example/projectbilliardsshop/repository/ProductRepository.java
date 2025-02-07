package org.example.projectbilliardsshop.repository;

import org.example.projectbilliardsshop.model.Brand;
import org.example.projectbilliardsshop.model.Category;
import org.example.projectbilliardsshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);

    Product getProductByProductId(int productId);

    // Tìm sản phẩm trong khoảng giá (min - max)
    List<Product> findByCategoryAndPriceBetween(Category category, BigDecimal minPrice, BigDecimal maxPrice);

    // Tìm sản phẩm có giá >= minPrice (không có giới hạn trên)
    List<Product> findByCategoryAndPriceGreaterThanEqual(Category category, BigDecimal minPrice);

    List<Product> findByBrand(Brand brand);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
