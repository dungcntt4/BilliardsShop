package org.example.projectbilliardsshop.initializer;

import com.github.javafaker.Faker;
import org.example.projectbilliardsshop.model.Brand;
import org.example.projectbilliardsshop.model.Category;
import org.example.projectbilliardsshop.model.Product;
import org.example.projectbilliardsshop.repository.BrandRepository;
import org.example.projectbilliardsshop.repository.CategoryRepository;
import org.example.projectbilliardsshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProductInitializer implements CommandLineRunner {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final Faker faker = new Faker();

    @Override
    public void run(String... args) throws Exception {
        List<Category> categories = categoryRepository.findAll();
        List<Brand> brands = brandRepository.findAll();
        List<Category> parentCategoriesWithSubcategories = categoryRepository.findAll().stream()
                .filter(category -> category.getChildren() != null && !category.getChildren().isEmpty())  // Lọc các danh mục cha có con
                .flatMap(category -> Stream.concat(Stream.of(category), category.getChildren().stream()))  // Thêm cả danh mục con vào
                .collect(Collectors.toList());

        if(productRepository.count() == 0) {
            for (int i = 0; i < 100; i++) {
                Product product = new Product();
                product.setProductName(faker.commerce().productName());
                product.setDescription(faker.lorem().sentence());
                product.setCategory(selectRandomCategoryWithChildren(parentCategoriesWithSubcategories));  // Chọn ngẫu nhiên category
                product.setBrand(brands.get(faker.random().nextInt(brands.size())));  // Chọn ngẫu nhiên brand
                product.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 100, 3000)));
                product.setUrl(generateUrl(product.getProductName()));
                product.setStock(faker.number().numberBetween(10, 100));
                product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                product.setUpdatedDate(new Timestamp(System.currentTimeMillis()));

                // Lưu sản phẩm vào cơ sở dữ liệu
                productRepository.save(product);
            }
        }



    }
    private String generateUrl(String name) {
        // Chuyển tên sản phẩm thành chữ thường, loại bỏ dấu cách và thay thế bằng dấu gạch ngang
        return name.toLowerCase().replaceAll(" ", "-").replaceAll("[^a-z0-9-]", "");
    }
    private Category selectRandomCategoryWithChildren(List<Category> categories) {
        return categories.get(faker.random().nextInt(categories.size()));
    }

}
