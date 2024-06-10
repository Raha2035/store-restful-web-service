package com.msho.store.rest.sebservice.storerestfulwebservice.repository;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Category;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);

    @Transactional
    void deleteByCategory(Category category);
}
