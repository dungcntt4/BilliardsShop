package org.example.projectbilliardsshop.service;

import org.example.projectbilliardsshop.model.Brand;
import org.example.projectbilliardsshop.model.Category;
import org.example.projectbilliardsshop.model.Product;
import org.example.projectbilliardsshop.repository.BrandRepository;
import org.example.projectbilliardsshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;

    public Product getProductByProductId(int productId) {
        return productRepository.getProductByProductId(productId);
    }

    public List<Product> findByCategory(Category category) {
        List <Product> products = new ArrayList<>(); ;
        if(category.getChildren() == null){
            products = productRepository.findByCategory(category);
            return products;
        }else {
            products.addAll(productRepository.findByCategory(category));
            for (Category child : category.getChildren()) {
                products.addAll(findByCategory(child));
            }
            return products;
        }

    }

    public List<Product> findByBrand(Brand brand) {
        return productRepository.findByBrand(brand);
    }

    public List<Product> findByCategoryAndPriceRange(Category category, BigDecimal minPrice, BigDecimal maxPrice) {
        if (maxPrice == null) {
            return productRepository.findByCategoryAndPriceGreaterThanEqual(category, minPrice);
        } else {
            return productRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice);
        }
    }

    public List<Product> filterProductsByPriceRange(Category category, List<String> priceRanges) {
        List<Product> filteredProducts = new ArrayList<>();

        for (String priceRange : priceRanges) {
            String cleanedPriceRange = priceRange.replace(",", "").trim();
            String[] range = cleanedPriceRange.split("-");
            BigDecimal minPrice, maxPrice;

            try {
                if (range.length == 2) {
                    minPrice = new BigDecimal(range[0]);
                    maxPrice = new BigDecimal(range[1]);
                } else if (cleanedPriceRange.endsWith("+")) {
                    minPrice = new BigDecimal(cleanedPriceRange.replace("+", ""));
                    maxPrice = null;
                } else {
                    continue; // Nếu format không hợp lệ, bỏ qua
                }

                filteredProducts.addAll(findByCategoryAndPriceRange(category, minPrice, maxPrice));

            } catch (NumberFormatException e) {
                System.err.println("Lỗi khi chuyển đổi số: " + e.getMessage());
            }
        }

        return filteredProducts.stream().distinct().collect(Collectors.toList());
    }

    public Brand getBrandByUrl(String brand) {
        return brandRepository.findByUrl(brand);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword);
    }


}
