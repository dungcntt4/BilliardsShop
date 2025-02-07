package org.example.projectbilliardsshop.repository;

import org.example.projectbilliardsshop.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Brand findByUrl(String url);
}
