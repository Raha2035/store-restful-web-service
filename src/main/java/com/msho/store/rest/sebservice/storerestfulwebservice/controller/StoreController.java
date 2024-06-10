package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Store;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.StoreRepository;
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

    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
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
}
