package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.*;
import com.msho.store.rest.sebservice.storerestfulwebservice.service.ImageService;
import com.msho.store.rest.sebservice.storerestfulwebservice.service.OrdersService;
import com.msho.store.rest.sebservice.storerestfulwebservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/*
* REST controller for managing users and their orders.
* */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrdersService ordersService;
    private final ImageService imageService;


    /*
    * Get all users.
    *
    * @return a list of all users
    * */
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /*
    * Get a user by ID.
    *
    * @param id the user ID
    * @return the user with the specified ID
    * */
    @GetMapping("/get-user/{id}")
    public ResponseEntity<User> getUserById(@NotNull @PathVariable int id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /*
    * Delete a user by ID.
    *
    * @param id the user ID
    * @return a response indicating the result of the deletion
    * */
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@NotNull @PathVariable int id){
       userService.deleteUserById(id);
       return ResponseEntity.ok("user successfully deleted.");
    }

    /*
    * Get all orders for a specific user.
    *
    * @param userId the user ID
    * @return a list of orders for the specified user
    * */
    @GetMapping("/{userId}/all-orders")
    public ResponseEntity<List<Orders>> getAllOrdersByUserId(@PathVariable int userId){
        List<Orders> orders = ordersService.findAllOrdersOfOneUser(userId);
        return ResponseEntity.ok(orders);
    }

    /*
    * Create a new order for a specific user.
    *
    * @param userId the user ID
    * @param order the order to create
    * @return a response indicating the result of the creation
    * */
    @PostMapping("/{userId}/create-order")
    public ResponseEntity<String> createOrder(@PathVariable int userId,
                                              @RequestBody Orders order){
        ordersService.createOrderForOneUser(userId ,order);
        return ResponseEntity.status( HttpStatus.CREATED).body("Order successfully saved");
    }

    /*
    * Delete an order by ID for a specific user
    *
    * @param userId the user ID
    * @param id the order ID
    * @return a response indicating the result of the deletion
    * */
    @DeleteMapping("/{userId}/delete-order/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable int userId,
                                              @PathVariable int id){
        ordersService.deleteOrderById(userId, id);
        return ResponseEntity.ok("Order successfully deleted");
    }

    /*
    * Update an order by ID for a specific user
    *
    * @param userId the user ID
    * @param id the order ID
    * @param order the updated order
    * @return a response indicating the result of the update
    * */
    @PutMapping("/{userId}/update-order/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable int userId,
                                              @PathVariable int id,
                                              @Valid @RequestBody Orders order){
        ordersService.modifyOrder(userId, id, order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order successfully updated");
    }

    /*
    * Upload an image for a specific user.
    *
    * @param id the user ID
    * @param file the image file
    * @return a response indicating the result of the uploading
    * */
    @PostMapping("/{id}/image")
    public ResponseEntity<?> uploadImage(@PathVariable int id,
                                         @RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = imageService.uploadImage(id, file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    /*
     * Download an image for a specific user.
     *
     * @param id the user ID
     * @param fileName the image's name
     * @return an image for a specific user
     * */
    @GetMapping("/{id}/download-image")
    public ResponseEntity<?> downloadImage(@PathVariable int id) {
        byte[] imageData = imageService.downloadImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                .body(imageData);
    }
}


