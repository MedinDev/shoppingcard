package com.medindev.shoppingcard.service.category;

import com.medindev.shoppingcard.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
    List<Category> getAllCategories();
}
