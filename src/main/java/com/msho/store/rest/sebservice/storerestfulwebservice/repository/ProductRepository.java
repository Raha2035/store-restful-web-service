package com.msho.store.rest.sebservice.storerestfulwebservice.repository;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Category;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, CrudRepository<Product, Integer> {
    List<Product> findByCategory(Category category);
}
