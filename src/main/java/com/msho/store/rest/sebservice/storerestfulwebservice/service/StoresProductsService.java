package com.msho.store.rest.sebservice.storerestfulwebservice.service;

import com.msho.store.rest.sebservice.storerestfulwebservice.exception.ItemNotFoundException;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Store;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.StoresProducts;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.StoresProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoresProductsService {
    private final StoresProductsRepository repository;

    public StoresProductsService(StoresProductsRepository repository) {
        this.repository = repository;
    }

    public StoresProducts findByStoreAndProduct(Store store, Product product) {
        Optional<StoresProducts> result = repository.findByStoreAndProduct(store, product);
        if (result.isEmpty())
            throw new ItemNotFoundException("Store with id: " + store.getID() + " doesn't have any product with id: " + product.getID());
        return result.get();
    }

    public void updateTotalCount(int id, int count) {
        repository.updateTotalCount(id, count);
    }

    public List<StoresProducts> findAllByStoreAndTotalCountGreaterThan(Store store, int i) {
        List<StoresProducts> productsOfStore = repository.findAllByStoreAndTotalCountGreaterThan(store, i);
        if (productsOfStore.isEmpty())
            throw new RuntimeException("Store with id: " + store.getID() + " does not have any products");
        return productsOfStore;
    }

    public void createProductForThisStore(StoresProducts storesProducts) {
        repository.save(storesProducts);
    }

    public void deleteByStoreAndProduct(Store store, Product product) {
        repository.deleteByStoreAndProduct(store, product);
    }

    public void deleteAllProductsOfThisStore(Store store) {
        repository.deleteAllByStore(store);
    }
}
