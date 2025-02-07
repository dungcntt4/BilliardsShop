package org.example.projectbilliardsshop.service;

import org.example.projectbilliardsshop.model.Category;
import org.example.projectbilliardsshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    // Lấy tất cả danh mục cha
    public List<Category> getAllParentCategories() {
        return categoryRepository.findByParentIsNull();
    }

    // Lấy danh mục con theo danh mục cha
    public List<Category> getChildrenByParent(Category parent) {
        return categoryRepository.findByParent(parent);
    }

    public Category getCategoryByUrl(String url) {
        return categoryRepository.findByUrl(url);
    }
}
