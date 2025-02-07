package org.example.projectbilliardsshop.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "productreview")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productReviewId;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    private int rating;
    private String comment;
    private Timestamp created_at;
    private Timestamp updated_at;
    @PrePersist
    protected void onCreate() {
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }
    public ProductReview() {
    }

    public ProductReview(User user, Product product, int rating, String comment) {
        this.user = user;
        this.product = product;
        this.rating = rating;
        this.comment = comment;
    }

    public int getProductReviewId() {
        return productReviewId;
    }

    public void setProductReviewId(int productReviewId) {
        this.productReviewId = productReviewId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
