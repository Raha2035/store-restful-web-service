package com.msho.store.rest.sebservice.storerestfulwebservice.repository;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Category;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);

    /*void deleteByCategory(Category category);*/


    /*@Modifying
    @Query(nativeQuery = true, value="DELETE FROM Product p WHERE p.category_id = ?1")
    void deleteByCategory_ID(int category_id);*/
}
