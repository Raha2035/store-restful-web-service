package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Category;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.CategoryRepository;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    public CategoryController(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/categories")
    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }

    @GetMapping("/categories/{id}")
    public Category findOneCategories(@PathVariable int id){
        Optional<Category> category = categoryRepository.findById(id);
        return category.get();
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> createCategory(@RequestBody Category category){
        Category savedCategory = categoryRepository.save(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCategory.getID()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/categories/{id}")
    public void deleteOneCategory(@PathVariable int id){
        categoryRepository.deleteById(id);
    }

    @PutMapping("/categories/{id}")
    public void modifyOneCategory(@PathVariable int id, @RequestBody Category category){
        Optional<Category> oldCategory = categoryRepository.findById(id);

        if (oldCategory.isEmpty())
            throw new RuntimeException("Category " + id + " not found");

        category.setID(oldCategory.get().getID());
        categoryRepository.save(category);

    }

    @PostMapping("/categories/{categoryId}/products")
    public ResponseEntity<Object> createProduct(@PathVariable int categoryId, @RequestBody Product product){
        Optional<Category> category = categoryRepository.findById(categoryId);

        if(category.isEmpty()){
            throw new RuntimeException("Category " + categoryId + " doesn't exist");
        }

        product.setCategory(category.get());
        Product savedProduct = productRepository.save(product);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}")
                        .buildAndExpand(savedProduct.getID()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/categories/{categoryId}/products")
    public List<Product> findAllProductsOfOneCategory(@PathVariable int categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);

        if(category.isEmpty()){
            throw new RuntimeException("Category " + categoryId + " doesn't exist");
        }

        return productRepository.findByCategory(category.get());
    }

    @GetMapping("/categories/{categoryId}/products/{productId}")
    public Product findOneProductsOfOneCategory(@PathVariable int categoryId, @PathVariable int  productId){
        Optional<Category> category = categoryRepository.findById(categoryId);

        if(category.isEmpty()){
            throw new RuntimeException("Category " + categoryId + " doesn't exist");
        }

        return productRepository.findById(productId).get();
    }

    @PutMapping("/categories/{categoryId}/products/{productId}")
    public void modifyOneProductOfOneCategory(@PathVariable int categoryId,
                                              @PathVariable int productId,
                                              @RequestBody Product product){
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty())
            throw new RuntimeException("Category " + categoryId + " not found");

        Optional<Product> oldProduct = productRepository.findById(productId);

        if (oldProduct.isEmpty())
            throw new RuntimeException("Product " + productId + " not found");

        product.setID(oldProduct.get().getID());
        product.setCategory(category.get());

        productRepository.save(product);
    }

    @DeleteMapping("/categories/{categoryId}/products/{productId}")
    public void deleteOneProductOfOneCategory(@PathVariable int categoryId, @PathVariable int productId){
        Optional<Category> category = categoryRepository.findById(categoryId);

        if(category.isEmpty())
            throw new RuntimeException("Category " + categoryId + " not found");

        productRepository.deleteById(productId);
    }
}

