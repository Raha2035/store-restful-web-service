package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.*;
import com.msho.store.rest.sebservice.storerestfulwebservice.service.OrdersService;
import com.msho.store.rest.sebservice.storerestfulwebservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final OrdersService ordersService;

    public UserController(UserService userService, OrdersService ordersService) {
        this.userService = userService;
        this.ordersService = ordersService;
    }

    @GetMapping("/users/all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.allUsers());
    }

    @GetMapping("/users/get-user/{id}")
    public ResponseEntity<User> getUserById(@NotNull @PathVariable int id){
        return ResponseEntity.ok(userService.getSpecificUser(id));
    }

    @DeleteMapping("/users/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@NotNull @PathVariable int id){
       userService.deleteSpecificUser(id);
       return ResponseEntity.ok("user successfully deleted.");
    }

    @GetMapping("/users/{userId}/all-orders")
    public ResponseEntity<List<Orders>> getAllOrdersByUserId(@PathVariable int userId){
        List<Orders> orders = ordersService.findAllOrdersOfOneUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/users/{userId}/create-order")
    public ResponseEntity<String> createOrder(@PathVariable int userId,
                                              @RequestBody Orders order){
        ordersService.createOrderForOneUser(userId ,order);
        return ResponseEntity.status( HttpStatus.CREATED).body("This order successfully saved");
    }

    @DeleteMapping("/users/{userId}/delete-order/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable int userId,
                                              @PathVariable int id){
        ordersService.deleteOrderById(userId, id);
        return ResponseEntity.ok("Order successfully deleted");
    }

    @PutMapping("/users/{userId}/update-order/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable int userId,
                                              @PathVariable int id,
                                              @Valid @RequestBody Orders order){
        ordersService.modifyOrder(userId, id, order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order successfully updated");
    }
}


