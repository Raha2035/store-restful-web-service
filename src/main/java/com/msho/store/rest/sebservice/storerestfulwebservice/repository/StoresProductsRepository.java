package com.msho.store.rest.sebservice.storerestfulwebservice.repository;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Store;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.StoresProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoresProductsRepository extends JpaRepository<StoresProducts, Integer> {
    List<StoresProducts> findAllByStoreAndTotalCountGreaterThan(Store store, int totalCount);

    @Transactional
    void deleteByStoreAndProduct(Store store, Product product);

    @Transactional
    void deleteAllByStore(Store store);

    Optional<StoresProducts> findByStoreAndProduct(Store store, Product product);

    @Modifying
    @Query("update StoresProducts sp set sp.totalCount = ?2 where sp.id = ?1")
    void updateTotalCount( int id, int count);
}
