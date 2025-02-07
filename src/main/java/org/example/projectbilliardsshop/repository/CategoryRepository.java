package org.example.projectbilliardsshop.repository;

import org.example.projectbilliardsshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Lấy danh mục cha (parent = null)
    List<Category> findByParentIsNull();

    List<Category> findByParent(Category parent);

    Category findByUrl(String url);
}
