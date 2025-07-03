package com.medindev.shoppingcard.service.product;

import com.medindev.shoppingcard.model.Product;
import com.medindev.shoppingcard.request.AddProductRequest;
import com.medindev.shoppingcard.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
   Product addProduct(AddProductRequest product);
   Product getProductById(Long id);
   Product updateProduct(ProductUpdateRequest product, Long ProductId);
   void deleteProductById(Long id);
   List<Product> getAllProducts();
   List<Product> getProductsByCategory(String category);
   List<Product> getProductsByBrand(String brand);
   List<Product> getProductsByCategoryAndBrand(String category, String brand);
   List<Product> getProductsByName(String name);
   List<Product> getProductsByBrandAndName(String name, String band);
   Long CountProductsByBrandAndName(String brand, String name);


}
