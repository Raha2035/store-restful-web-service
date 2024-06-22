package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Store;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.StoresProducts;
import com.msho.store.rest.sebservice.storerestfulwebservice.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/stores/all-stores")
    public ResponseEntity<Object> findAllStores(){
        return new ResponseEntity<>(storeService.getAllStores(), HttpStatus.OK);
    }

    @GetMapping("/stores/get-store/{id}")
    public ResponseEntity<Object> findOneStore(@PathVariable int id){
        Store store = storeService.findStoreById(id);
        return new ResponseEntity<>(store, HttpStatus.OK);
    }

    @PostMapping("/stores/create-store")
    public ResponseEntity<Object> createStore(@Valid @RequestBody Store store){
        storeService.saveStore(store);
        return new ResponseEntity<>("This store successfully created", HttpStatus.CREATED);
    }

    @DeleteMapping("/stores/delete-store/{id}")
    public ResponseEntity<Object> deleteOneStore(@PathVariable int id){
        storeService.deleteStoreById(id);
        return new ResponseEntity<>("Store with id: " + id + "successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/stores/update-store/{id}")
    public ResponseEntity<Object> modifyStore(@PathVariable int id,@Valid @RequestBody Store store){
        storeService.editStore(id, store);
        return new ResponseEntity<>("This store successfully updated.", HttpStatus.CREATED);
    }

    @GetMapping("/stores/{id}/all-products")
    public ResponseEntity<Object> findAllAvailableProductsOfOneStore(@PathVariable int id){
        List<StoresProducts> productsOfStore = storeService.findAllAvailableProductsOfOneStore(id);
        return new ResponseEntity<>(productsOfStore, HttpStatus.OK);
    }

    @PostMapping("/stores/{id}/create-products")
    public ResponseEntity<Object> createProductsForOneStore(@PathVariable int id , @Valid @RequestBody StoresProducts storesProducts){
        storeService.createProductForThisStore(id, storesProducts);
        return new ResponseEntity<>("Product add to store successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/stores/{storeId}/products/delete-product/{productId}")
    public ResponseEntity<Object> deleteOneProductOfOneStore(@PathVariable int storeId, @PathVariable int productId){
        storeService.deleteOneProductOfOneStore(storeId,  productId);
        return new ResponseEntity<>(
                "Product with id: " + productId + " deleted from store with id: " + storeId,
                      HttpStatus.OK);
    }

    @DeleteMapping("/stores/{storeId}/products/delete-all-products")
    public ResponseEntity<Object> deleteAllProductOfOneStore(@PathVariable int storeId){
        storeService.deleteAllProductsOfOneStore(storeId);
        return new ResponseEntity<>("All products of store with id: " + storeId + " successfully deleted",HttpStatus.OK);
    }
}
