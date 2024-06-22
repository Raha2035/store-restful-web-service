package com.msho.store.rest.sebservice.storerestfulwebservice.service;

import com.msho.store.rest.sebservice.storerestfulwebservice.exception.ItemNotFoundException;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Store;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.StoresProducts;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository repository;

    private final StoresProductsService storesProductsService;

    private final ProductService productService;

    public StoreService(StoreRepository repository, StoresProductsService storesProductsService, ProductService productService) {
        this.repository = repository;
        this.storesProductsService = storesProductsService;
        this.productService = productService;
    }

    public Store findStoreById(int id) {
        Optional<Store> store = repository.findById(id);
        if (store.isEmpty())
            throw new ItemNotFoundException("Store not found with id: " + id);
        return store.get();
    }

    public List<Store> getAllStores() {
        return repository.findAll();
    }

    public void saveStore(Store store) {
        repository.save(store);
    }

    public void deleteStoreById(int id) {
        this.findStoreById(id);
        repository.deleteById(id);
    }

    public void editStore(int id, Store store) {
        Store oldStore = this.findStoreById(id);
        oldStore.setID(id);
        oldStore.setName(store.getName());
        repository.save(oldStore);
    }

    public List<StoresProducts> findAllAvailableProductsOfOneStore(int id) {
        Store store = this.findStoreById(id);
        return storesProductsService.findAllByStoreAndTotalCountGreaterThan(store, 0);
    }

    public void createProductForThisStore(int id, StoresProducts storesProducts) {
        this.findStoreById(id);
        Product product = storesProducts.getProduct();
        int productId =  product != null ? product.getID() : -1;
        if (productId > 0)
            productService.findProductById(productId);
        storesProductsService.createProductForThisStore(storesProducts);

    }

    public void deleteOneProductOfOneStore(int storeId, int productId) {
        Store store = this.findStoreById(storeId);
        Product product = productService.findProductById(productId);
        storesProductsService.deleteByStoreAndProduct(store, product);
    }

    public void deleteAllProductsOfOneStore(int storeId) {
        Store store = this.findStoreById(storeId);
        storesProductsService.deleteAllProductsOfThisStore(store);
    }
}
