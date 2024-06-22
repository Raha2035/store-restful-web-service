package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Category;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;

import com.msho.store.rest.sebservice.storerestfulwebservice.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories/all-categories")
    public ResponseEntity<Object> findAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/categories/get-category/{id}")
    public ResponseEntity<Category> findOneCategories(@PathVariable int id){
        Category category = categoryService.findCategoryById(id);
        return new ResponseEntity<>(category , HttpStatus.OK);
    }

    @PostMapping("/categories/create-category")
    public ResponseEntity<Object> createCategory(@RequestBody Category category){
        categoryService.saveCategory(category);
        return new ResponseEntity<>("Category successfully created", HttpStatus.CREATED);
    }

    @DeleteMapping("/categories/delete-category/{id}")
    public ResponseEntity<Object> deleteOneCategory(@PathVariable int id){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>("Category successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/categories/update-category/{id}")
    public ResponseEntity<Object> modifyOneCategory(@PathVariable int id, @RequestBody Category category){
        categoryService.editCategory(id, category);
        return new ResponseEntity<>("Category successfully updated", HttpStatus.OK);
    }

    @PostMapping("/categories/{categoryId}/products/create-product")
    public ResponseEntity<Object> createProduct(@PathVariable int categoryId, @RequestBody Product product){
        categoryService.createProductForThisCategory(categoryId, product);
        return new ResponseEntity<>("Product create successfully", HttpStatus.CREATED);
    }

    @GetMapping("/categories/{categoryId}/products/all-products")
    public ResponseEntity<Object> findAllProductsOfOneCategory(@PathVariable int categoryId){
        List<Product> products =  categoryService.findAllProductsOfSpecificCategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/products/get-product/{productId}")
    public ResponseEntity<Object> findOneProductsOfOneCategory(@PathVariable int categoryId, @PathVariable int  productId){
        Product product = categoryService.findOneProductOfOneCategory(categoryId, productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/categories/{categoryId}/products/update-product/{productId}")
    public ResponseEntity<Object> modifyOneProductOfOneCategory(@PathVariable int categoryId,
                                              @PathVariable int productId,
                                              @RequestBody Product product){
        categoryService.saveProductOfThisCategory(categoryId, productId, product);
        return new ResponseEntity<>("Update successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/categories/{categoryId}/products/delete-product/{productId}")
    public ResponseEntity<Object> deleteOneProductOfOneCategory(@PathVariable int categoryId, @PathVariable int productId){
        categoryService.deleteOneProductOfOneCategory(categoryId, productId);
        return new ResponseEntity<>("delete is Ok", HttpStatus.OK);
    }

    @DeleteMapping("/categories/{categoryId}/products/delete-all-products")
    public ResponseEntity<Object> deleteAllProductsOfOneCategory(@PathVariable int categoryId){
         categoryService.deleteAllProductsOfOneCategory(categoryId);
        return new ResponseEntity<>("delete is Ok", HttpStatus.OK);
    }
}

