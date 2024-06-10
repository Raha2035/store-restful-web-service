package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Store;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.StoresProducts;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.ProductRepository;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.StoreRepository;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.StoresProductsRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class StoreController {
    private final StoreRepository storeRepository;

    private final StoresProductsRepository storesProductsRepository;

    private final ProductRepository productRepository;

    public StoreController(StoreRepository storeRepository,
                           StoresProductsRepository storesProductsRepository,
                           ProductRepository productRepository) {
        this.storeRepository = storeRepository;
        this.storesProductsRepository = storesProductsRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/stores")
    public List<Store> findAllStores(){
        return storeRepository.findAll();
    }

    @GetMapping("/stores/{id}")
    public Store findOneStore(@PathVariable int id){
        Optional<Store> store = storeRepository.findById(id);

        if(store.isEmpty())
            throw new RuntimeException("Store " + id + " does not exist");

        return store.get();
    }

    @PostMapping("/stores")
    public ResponseEntity<Object> createStore(@Valid @RequestBody Store store){
        Store savedStore = storeRepository.save(store);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStore.getID()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/stores/{id}")
    public void deleteOneStore(@PathVariable int id){
        storeRepository.deleteById(id);
    }

    @PutMapping("/stores/{id}")
    public void modifyStore(@PathVariable int id, @RequestBody Store store){
        Optional<Store> oldStore = storeRepository.findById(id);

        if(oldStore.isEmpty()){
            throw new RuntimeException("Store doesn't exist");
        }

        store.setID(oldStore.get().getID());
        storeRepository.save(store);
    }

    @GetMapping("/stores/{id}/products")
    public List<StoresProducts> findAllAvailableProductsOfOneStore(@PathVariable int id){
        Optional<Store> store = storeRepository.findById(id);

        if(store.isEmpty())
            throw new RuntimeException("Store " + id + " does not exist");

        List<StoresProducts> rows = storesProductsRepository.findAllByStoreAndTotalCountGreaterThan(store.get(), 0);
        return  rows;
    }

    @PostMapping("/stores/{id}/products")
    public ResponseEntity<Object> createProductsForOneStore(@PathVariable int id , @Valid @RequestBody StoresProducts storesProducts){
        Optional<Store> store = storeRepository.findById(id);

        if(store.isEmpty())
            throw new RuntimeException("Store " + id + " does not exist");

        int productId = storesProducts.getProduct().getID();
        Optional<Product> oldProduct = productRepository.findById(productId);

        if (oldProduct.isEmpty())
            throw new RuntimeException("Product " + productId + " not found");

        StoresProducts savedStoresProducts = storesProductsRepository.save(storesProducts);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStoresProducts.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/stores/{storeId}/products/{productId}")
    public void deleteOneProductOfOneStore(@PathVariable int storeId, @PathVariable int productId){
        Optional<Store> store = storeRepository.findById(storeId);

        if(store.isEmpty())
            throw new RuntimeException("Store " + storeId + " does not exist");

        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty())
            throw new RuntimeException("Product " + productId + " not found");

        storesProductsRepository.deleteByStoreAndProduct(store.get(), product.get());
    }

    @DeleteMapping("/stores/{storeId}/products")
    public void deleteAllProductOfOneStore(@PathVariable int storeId){
        Optional<Store> store = storeRepository.findById(storeId);

        if(store.isEmpty())
            throw new RuntimeException("Store " + storeId + " does not exist");

        storesProductsRepository.deleteAllByStore(store.get());
    }
}
