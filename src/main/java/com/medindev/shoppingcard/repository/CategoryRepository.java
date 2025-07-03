package com.medindev.shoppingcard.repository;

import com.medindev.shoppingcard.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
   Category findByName(String name);
}
