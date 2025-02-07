package org.example.projectbilliardsshop.service;

import org.example.projectbilliardsshop.model.Product;
import org.example.projectbilliardsshop.model.ProductReview;
import org.example.projectbilliardsshop.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductReviewService {
    @Autowired
    private ProductReviewRepository productReviewRepository;
    public Boolean addProductReview(ProductReview productReview) {
        return (productReviewRepository.save(productReview) != null);
    }

    public List<ProductReview> getProductReviewsByProductId(Product product) {
        List<ProductReview> productReviews = productReviewRepository.findByProduct(product);
        return productReviews;
    }

    public double averageRating(Product product) {
        List<ProductReview> productReviews = productReviewRepository.findByProduct(product);
        if(productReviews.size() == 0) {return 0;
        }else {
            double sum = 0;
            for (ProductReview productReview : productReviews) {
                sum += productReview.getRating();
            }
            double average = sum / productReviews.size();
            BigDecimal bd = new BigDecimal(average).setScale(1, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }

    public int totalRating(Product product) {
        List<ProductReview> productReviews = productReviewRepository.findByProduct(product);
        return productReviews.size();
    }

    public Map<Integer, Double> getRatingsDistribution(Product product) {
        // Lấy danh sách đánh giá của sản phẩm từ productId
        List<ProductReview> productReviews = productReviewRepository.findByProduct(product);
        Map<Integer, Double> ratingsDistribution = new LinkedHashMap<>();
        if (productReviews.isEmpty()) {
            for (int i = 5; i >= 1; i--) {
                ratingsDistribution.put(i, 0.0);
            }
            return ratingsDistribution;
        }else {
            // Khởi tạo map để lưu trữ số lượng đánh giá cho mỗi mức sao
            Map<Integer, Integer> ratingsCount = new LinkedHashMap<>();
            for (int i = 5; i >= 1; i--) {
                ratingsCount.put(i, 0);
            }

            // Đếm số lượng đánh giá cho mỗi mức sao
            for (ProductReview review : productReviews) {
                int rating = review.getRating();
                ratingsCount.put(rating, ratingsCount.get(rating) + 1);
            }

            // Tính toán phần trăm cho mỗi mức sao và làm tròn số double


            int totalReviews = productReviews.size();
            DecimalFormat df = new DecimalFormat("#.#");

            for (int i = 5; i >= 1; i--) {
                int count = ratingsCount.getOrDefault(i, 0);
                double percentage = (count / (double) totalReviews) * 100;
                double roundedPercentage = Double.parseDouble(df.format(percentage));
                ratingsDistribution.put(i, roundedPercentage);
            }

            return ratingsDistribution;
        }
    }

}
