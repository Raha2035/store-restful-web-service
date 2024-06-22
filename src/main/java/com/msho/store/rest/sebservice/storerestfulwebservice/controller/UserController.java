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
    public ResponseEntity<Object> findAllUsers(){
        return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/get-user/{id}")
    public ResponseEntity<User> findOneUser(@NotNull @PathVariable int id){
        return new ResponseEntity<>(
                userService.getSpecificUser(id)
                , HttpStatus.OK);
    }

    @DeleteMapping("/users/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@NotNull @PathVariable int id){
       userService.deleteSpecificUser(id);
       return new ResponseEntity<>("user successfully deleted.", HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/all-orders")
    public ResponseEntity<Object> findAllOrdersOfOneUser(@PathVariable int userId){
        List<Orders> orders = ordersService.findAllOrdersOfOneUser(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/create-order")
    public ResponseEntity<Object> createOrderForOneUser(@PathVariable int userId, @RequestBody Orders order){
        ordersService.createOrderForOneUser(userId ,order);
        return new ResponseEntity<>("This order successfully saved", HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{userId}/delete-order/{id}")
    public ResponseEntity<Object> deleteOneOrderById(@PathVariable int userId, @PathVariable int id){
        ordersService.deleteOrderById(userId, id);
        return new ResponseEntity<>("Order successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/update-order/{id}")
    public ResponseEntity<Object> modifyOrder(@PathVariable int userId, @PathVariable int id, @Valid @RequestBody Orders order){
        ordersService.modifyOrder(userId, id, order);
        return new ResponseEntity<>("Order successfully updated", HttpStatus.CREATED);
    }
}


