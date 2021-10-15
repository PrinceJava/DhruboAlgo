package com.project2.dhrubosalgorithms.repository;


import com.project2.dhrubosalgorithms.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String categoryName);
    List<Category> findByUserId(Long userId);
    Category findByUserIdAndName(Long userId, String algorithmCategoryId);
}
