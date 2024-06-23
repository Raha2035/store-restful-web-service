package com.msho.store.rest.sebservice.storerestfulwebservice.service;

import com.msho.store.rest.sebservice.storerestfulwebservice.exception.ItemNotFoundException;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Category;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public Product findProductById(int id) {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty())
            throw new ItemNotFoundException("Product not found with id: " + id );
        return product.get();
    }

    public void saveProduct(Product product) {
        repository.save(product);
    }

    public List<Product> findAllProductsOfSpecificCategory(int categoryId) {
        return repository.findAllByCategoryID(categoryId);
    }

    public void deleteProduct(Product product) {
        repository.delete(product);
    }

    public void deleteAllProductsOfOneCategory(Category category) {
        repository.deleteByCategory(category);
    }
}
