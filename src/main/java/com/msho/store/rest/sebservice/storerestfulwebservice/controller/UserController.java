package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.*;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.*;
import com.msho.store.rest.sebservice.storerestfulwebservice.service.OrdersService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository userRepository;

    private final OrdersService ordersService;



    public UserController(UserRepository userRepository,
                          OrdersService ordersService) {

        this.userRepository = userRepository;
        this.ordersService = ordersService;

    }

    @GetMapping("/users/all-users")
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/get-user/{id}")
    public User findOneUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new RuntimeException("User " + id + " not found");
        }

        return user.get();
    }

    @PostMapping("/users/create-user")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getID()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/delete-user/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.findById(id);
    }

    @PutMapping("/users/update-user/{id}")
    public void modifyUser(@PathVariable int id, @RequestBody User user){
        Optional<User> oldUser = userRepository.findById(id);

        if(oldUser.isEmpty()){
            throw new RuntimeException("User doesnt exist");
        }

        user.setID(oldUser.get().getID());
        userRepository.save(user);
    }

    @GetMapping("/users/{userId}/all-orders")
    public List<Orders> findAllOrdersOfOneUser(@PathVariable int userId){
        return ordersService.findAllOrdersOfOneUser(userId);
    }

    @PostMapping("/users/{userId}/create-order")
    public ResponseEntity<Object> createOrderForOneUser(@PathVariable int userId, @RequestBody Orders orders){
        Orders savedOrders = ordersService.createOrderForOneUser(userId ,orders);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(savedOrders.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{userId}/delete-order/{id}")
    public void deleteOneOrderById(@PathVariable int userId, @PathVariable int id){
        ordersService.deleteOrderById(userId, id);
    }

    @PutMapping("/users/{userId}/update-order/{id}")
    public void modifyOrder(@PathVariable int userId, @PathVariable int id, @Valid @RequestBody Orders order){
        ordersService.modifyOrder(userId, id, order);
    }
}


