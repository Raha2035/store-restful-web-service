package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Store;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.StoresProducts;
import com.msho.store.rest.sebservice.storerestfulwebservice.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* REST controller for managing stores and their products
* */
@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    /*
    * Constructor for StoreController
    *
    * @param storeService the store service
    * */
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    /*
    * Get all stores.
    *
    * @return a list of all stores
    * */
    @GetMapping("/all-stores")
    public ResponseEntity<List<Store>> getAllStores(){
        return ResponseEntity.ok(storeService.getAllStores());
    }

    /*
    * Get a store by ID
    *
    * @param id the store ID
    * @return the store with the specified ID
    * */
    @GetMapping("/get-store/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable int id){
        Store store = storeService.findStoreById(id);
        return ResponseEntity.ok(store);
    }

    /*
    * Create a new store.
    *
    * @param store the store to create
    * @return a response indicating the result of the creation
    * */
    @PostMapping("/create-store")
    public ResponseEntity<String> createStore(@Valid @RequestBody Store store){
        storeService.saveStore(store);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Store successfully created");
    }

    /*
    * Delete a store by ID.
    *
    * @param id the store ID
    * @return a response indicating the result of the deletion
    * */
    @DeleteMapping("/delete-store/{id}")
    public ResponseEntity<String> deleteStore(@PathVariable int id){
        storeService.deleteStoreById(id);
        return ResponseEntity.ok("Store with id: " + id + "successfully deleted");
    }

    /*
    * Update a store by ID.
    *
    * @param id the store ID
    * @param store the updated store
    * @return a response indicating the result of the update
    * */
    @PutMapping("/update-store/{id}")
    public ResponseEntity<String> updateStore(@PathVariable int id, @Valid @RequestBody Store store){
        storeService.editStore(id, store);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Store successfully updated.");
    }

    /*
    * Get all products for a specific store.
    *
    * @param id the store ID
    * @return a list of products for the specified store
    * */
    @GetMapping("/{id}/all-products")
    public ResponseEntity<List<StoresProducts>> getAllProductsByStoreId(@PathVariable int id){
        List<StoresProducts> productsOfStore = storeService.findAllAvailableProductsOfOneStore(id);
        return ResponseEntity.ok(productsOfStore);
    }

    /*
    * Define a new product for a specific store.
    *
    * @param id the store ID
    * @param storesProducts the product to create
    * @return a response indicating the result of the creation
    * */
    @PostMapping("/{id}/create-products")
    public ResponseEntity<String> createProduct(
            @PathVariable int id ,
            @Valid @RequestBody StoresProducts storesProducts){
        storeService.createProductForThisStore(id, storesProducts);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Product added to store successfully");
    }

    /*
    * Delete a product by ID for a specific store.
    *
    * @param storeId the store ID
    * @param productId the product ID
    * @return a response indicating the result of the deletion
    * */
    @DeleteMapping("/{storeId}/products/delete-product/{productId}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable int storeId,
            @PathVariable int productId){
        storeService.deleteOneProductOfOneStore(storeId,  productId);
        return ResponseEntity.ok(
                "Product with id: " + productId + " deleted from store with id: " + storeId);
    }

    /*
    * Delete all products for a specific store.
    *
    * @param storeId the store ID
    * @return a response indicating the result of the deletion
    * */
    @DeleteMapping("/{storeId}/products/delete-all-products")
    public ResponseEntity<String> deleteAllProducts(@PathVariable int storeId){
        storeService.deleteAllProductsOfOneStore(storeId);
        return ResponseEntity.ok("All products of store with id: " + storeId + " successfully deleted");
    }
}
