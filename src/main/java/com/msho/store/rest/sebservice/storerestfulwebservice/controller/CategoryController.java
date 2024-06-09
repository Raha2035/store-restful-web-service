package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Category;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
}

