package com.msho.store.rest.sebservice.storerestfulwebservice.repository;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Store;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.StoresProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.transaction.Transactional;
import java.util.List;

public interface StoresProductsRepository extends JpaRepository<StoresProducts, Integer> {
    List<StoresProducts> findAllByStoreAndTotalCountGreaterThan(Store store, int totalCount);

    @Transactional
    void deleteByStoreAndProduct(Store store, Product product);

    @Transactional
    void deleteAllByStore(Store store);
}
