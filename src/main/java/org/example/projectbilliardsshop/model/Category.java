package org.example.projectbilliardsshop.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động sinh ID
    private int categoryId;
    private String categoryName;
    private String url;
    @ManyToOne(fetch = FetchType.EAGER) // Mối quan hệ cha -> con
    @JoinColumn(name = "parentId")
    @JsonIgnoreProperties("children")// Cột để lưu trữ id của mục cha
    private Category parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("parent") // Mối quan hệ ngược từ con về cha
    private List<Category> children;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
    public Category() {

    }

    public Category(String categoryName, String url) {
        this.categoryName = categoryName;
        this.url = url;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Category getParent() {
        return parent;
    }
    public void setParent(Category parent) {
        this.parent = parent;
    }
    public List<Category> getChildren() {
        return children;
    }
    public void setChildren(List<Category> children) {
        this.children = children;
    }


}
