package com.msho.store.rest.sebservice.storerestfulwebservice.service;

import com.msho.store.rest.sebservice.storerestfulwebservice.exception.ItemNotFoundException;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Category;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    private final ProductService productService;

    public CategoryService(CategoryRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public Category findCategoryById(int id) {
        Optional<Category> category = repository.findById(id);
        if (category.isEmpty())
            throw new ItemNotFoundException("Category not found with id: " + id);
        return category.get();
    }

    public void saveCategory(Category category) {
        repository.save(category);
    }

    public void deleteCategoryById(int id) {
        this.findCategoryById(id);
        repository.deleteById(id);
    }

    public void editCategory(int id, Category category) {
        Category oldCategory = this.findCategoryById(id);
        oldCategory.setName(category.getName());
        oldCategory.setAvailable(category.getAvailable());
        oldCategory.setValue(category.getValue());
        repository.save(oldCategory);
    }

    public void createProductForThisCategory(int categoryId, Product product) {
        Category category = this.findCategoryById(categoryId);
        product.setCategory(category);
        productService.saveProduct(product);
    }

    public List<Product> findAllProductsOfSpecificCategory(int categoryId) {
        this.findCategoryById(categoryId);
        return productService.findAllProductsOfSpecificCategory(categoryId);
    }

    public Product findOneProductOfOneCategory(int categoryId , int productId) {
        List<Product> products = this.findAllProductsOfSpecificCategory(categoryId);
        Product product = productService.findProductById(productId);
        if(!products.contains(product))
            throw new RuntimeException("This category does not have product with id: " + productId);
        return product;

    }

    public void saveProductOfThisCategory(int categoryId, int productId, Product product) {
        Category category = this.findCategoryById(categoryId);
        Product oldProduct = productService.findProductById(productId);
        product.setID(oldProduct.getID());
        product.setCategory(category);
        productService.saveProduct(product);
    }

    public void deleteOneProductOfOneCategory(int categoryId, int productId) {
        this.findCategoryById(categoryId);
        Product product = productService.findProductById(productId);
        List<Product> products = productService.findAllProductsOfSpecificCategory(categoryId);
        if(!products.contains(product))
            throw new RuntimeException("This category does not have product with id: " + productId);
        productService.deleteProduct(product);
    }

    public void deleteAllProductsOfOneCategory(int categoryId) {
        Category category = this.findCategoryById(categoryId);
        productService.deleteAllProductsOfOneCategory(category);
    }
}
