package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Category;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;

import com.msho.store.rest.sebservice.storerestfulwebservice.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* REST controller for managing categories and their products
* */
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    /*
     * Constructor for CategoryController.
     *
     * @param categoryService the category service
     * */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /*
    * Get all categories.
    *
    * @return a list of all categories
    * */
    @GetMapping("/all-categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /*
     * Get a category by ID.
     *
     * @param id the category ID
     * @return the category with the specified ID
     * */
    @GetMapping("/get-category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id){
        Category category = categoryService.findCategoryById(id);
        return ResponseEntity.ok(category);
    }

    /*
     * Create a new category.
     *
     * @param category the category to create
     * @return a response indicating the result of the creation
     * */
    @PostMapping("/create-category")
    public ResponseEntity<String> createCategory(@RequestBody Category category){
        categoryService.saveCategory(category);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Category successfully created");
    }

    /*
     * Delete a category by ID.
     *
     * @param id the category ID
     * @return a response indicating the result of the deletion
     * */
    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Category successfully deleted");
    }

    /*
     * Update a category by ID.
     *
     * @param id the category ID
     * @param category the updated category
     * @return a response indicating the result of the update
     * */
    @PutMapping("/update-category/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable int id,
            @RequestBody Category category){
        categoryService.editCategory(id, category);
        return ResponseEntity.ok("Category successfully updated");
    }

    /*
    * Create a new product for a specific category.
    *
    * @param categoryId the category ID
    * @param product the product to create
    * @return a response indicating the result of the creation
    * */
    @PostMapping("/{categoryId}/products/create-product")
    public ResponseEntity<String> createProduct(
            @PathVariable int categoryId,
            @RequestBody Product product){
        categoryService.createProductForThisCategory(categoryId, product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Product create successfully");
    }

    /*
    * Get all products for a specific category.
    *
    * @param categoryId the category ID
    * @return a list of products for the specified category
    * */
    @GetMapping("/{categoryId}/products/all-products")
    public ResponseEntity<List<Product>> getAllProductsByCategoryId(@PathVariable int categoryId){
        List<Product> products =  categoryService.findAllProductsOfSpecificCategory(categoryId);
        return ResponseEntity.ok(products);
    }


    /*
    * Get a product by ID for a specific category.
    *
    * @param categoryId the category ID
    * @param productId the product ID
    *
    * @return the product with the specified ID for the specified category
    * */
    @GetMapping("/{categoryId}/products/get-product/{productId}")
    public ResponseEntity<Product> getProductById(
            @PathVariable int categoryId,
            @PathVariable int  productId){
        Product product = categoryService.findOneProductOfOneCategory(categoryId, productId);
        return ResponseEntity.ok(product);
    }

    /*
    * Update a product by ID for a specific category.
    *
    * @param categoryId the category ID
    * @param productId the product ID
    * @param product the updated product
    * @return a response indicating the result of the update
    * */
    @PutMapping("/{categoryId}/products/update-product/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable int categoryId,
                                                @PathVariable int productId,
                                                @RequestBody Product product){
        categoryService.saveProductOfThisCategory(categoryId, productId, product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Update successfully");
    }

    /*
    * Delete a product by ID for a specific category.
    *
    * @param categoryId the category ID
     * @param productId the product ID
     * @return a response indicating the result of the deletion
    * */
    @DeleteMapping("/{categoryId}/products/delete-product/{productId}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable int categoryId,
            @PathVariable int productId){
        categoryService.deleteOneProductOfOneCategory(categoryId, productId);
        return ResponseEntity.ok("delete is Ok");
    }

    /*
     * Delete all products for a specific category.
     *
     * @param categoryId the category ID
     * @return a response indicating the result of the deletion
     * */
    @DeleteMapping("/{categoryId}/products/delete-all-products")
    public ResponseEntity<String> removeAllProductsFromCategory(@PathVariable int categoryId){
         categoryService.deleteAllProductsOfOneCategory(categoryId);
        return ResponseEntity.ok("delete is Ok");
    }
}

