package com.medindev.shoppingcard.controller;

import com.medindev.shoppingcard.exception.AlreadyExistsException;
import com.medindev.shoppingcard.exception.ResourceNotFoundException;
import com.medindev.shoppingcard.model.Category;
import com.medindev.shoppingcard.response.ApiResponse;
import com.medindev.shoppingcard.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/category")  // Base URL for all category-related endpoints
public class CategoryController {

    private final ICategoryService categoryService;

    /**
     * Retrieves all categories.
     *
     * @return ResponseEntity containing the list of categories or an error message.
     * @throws Exception If an error occurs while retrieving categories.
     * @author Medindev
     * @since 1.0.0
     **/

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Categories retrieved successfully", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error: " + e.getMessage(), null));
        }
    }

    /**
     * Adds a new category.
     *
     * @param category The category to be added.
     * @return ResponseEntity containing the added category or an error message.
     * @throws Exception If an error occurs while adding the category.
     * @author Medindev
     * @since 1.0.0
     **/

   @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(Category category) {
        try {
            Category savedCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Category added successfully", savedCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Error: " + e.getMessage(), null));
        }
    }


    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to be retrieved.
     * @return ResponseEntity containing the category or an error message.
     * @throws Exception If an error occurs while retrieving the category.
     * @author Medindev
     * @since 1.0.0
     **/

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Category retrieved successfully", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error: " + e.getMessage(), null));
        }
    }

    /**
     * Retrieves a category by its name.
     *
     * @param name The name of the category to be retrieved.
     * @return ResponseEntity containing the category or an error message.
     * @throws Exception If an error occurs while retrieving the category.
     * @author Medindev
     * @since 1.0.0
     **/

    @GetMapping("category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Category retrieved successfully", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error: " + e.getMessage(), null));
        }
    }


    /**
     * Deletes a category by its ID.
     * @param id The ID of the category to be deleted.
     * @return ResponseEntity containing the deleted category or an error message.
     * @throws Exception If an error occurs while deleting the category.
     * @author Medindev
     * @since 1.0.0
     **/
    @DeleteMapping("category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error: " + e.getMessage(), null));
        }
    }

    /**
     * Updates a category by its ID.
     * @param id The ID of the category to be updated.
     * @param category The updated category.
     * @return ResponseEntity containing the updated category or an error message.
     * @throws Exception If an error occurs while updating the category.
     * @author Medindev
     * @since 1.0.0
     * **/

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Category updated successfully", updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error: " + e.getMessage(), null));
        }
    }



}
