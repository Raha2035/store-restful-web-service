package com.msho.store.rest.sebservice.storerestfulwebservice.service;

import com.msho.store.rest.sebservice.storerestfulwebservice.exception.ItemNotFoundException;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.*;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    private final UserService userService;
    private final OrdersRepository ordersRepository;

    private final StoreService storeService;

    private final ProductService productService;

    private final StoresProductsService storesProductsService;

    public OrdersService(UserService userService, OrdersRepository ordersRepository, StoreService storeService, ProductService productService, StoresProductsService storesProductsService) {
        this.userService = userService;
        this.ordersRepository = ordersRepository;
        this.storeService = storeService;
        this.productService = productService;
        this.storesProductsService = storesProductsService;
    }

    private User getUserById(int id){
        return userService.getUserById(id);
    }

    private Store getStoreById(int id){
        return storeService.findStoreById(id);
    }

    private Product getProductById(int id){
        return productService.findProductById(id);
    }

    private StoresProducts getStoresProductsByStoreAndProduct(Store store, Product product){
        return storesProductsService.findByStoreAndProduct(store, product);
    }

    public List<Orders> findAllOrdersOfOneUser(int userId){
        User user = this.getUserById(userId);
        List<Orders> orders = ordersRepository.findAllByUser(user);
        if (orders.isEmpty())
            throw new ItemNotFoundException("This user doesn't have any orders");
        return orders;
    }

    @Transactional
    public void createOrderForOneUser(int userId, Orders order){
        User user = this.getUserById(userId);

        Store store = this.getStoreById(order.getStore().getID());

        Product product = this.getProductById(order.getProduct().getID());

        StoresProducts storesProducts = getStoresProductsByStoreAndProduct(store, product);
        int totalCount = storesProducts.getTotalCount();

        if(order.getNumber() > totalCount || totalCount == 0)
            throw new RuntimeException("Not enough inventory");

        double totalPrice = order.getNumber() * product.getFee();

        order.setUser(user);
        order.setStore(store);
        order.setProduct(product);
        order.setTotalPrice(totalPrice);

        Orders savedOrder = ordersRepository.findByUserAndStoreAndProduct(user, store, product);

        if( savedOrder != null)
            ordersRepository.updateByUserAndStoreAndProduct(user, store, product, order.getNumber());
        else
            savedOrder = ordersRepository.save(order);

        int count = totalCount - savedOrder.getNumber();
        storesProductsService.updateTotalCount(storesProducts.getId(), count);
    }

    @Transactional
    public void deleteOrderById(int userId, int id){
        Optional<Orders> result = ordersRepository.findById(id);
        if (result.isEmpty())
            throw new ItemNotFoundException("Order: " + id + " does not exist");

        Orders order = result.get();
        if(order.getUser().getId() != userId)
            throw new RuntimeException("Access Denied");
        int numberOfOrderProduct = order.getNumber();
        ordersRepository.delete(order);

        StoresProducts mappedStoreProduct = storesProductsService.findByStoreAndProduct(order.getStore(), order.getProduct());
        int inventory = mappedStoreProduct.getTotalCount();

        int totalCount = inventory + numberOfOrderProduct;

        storesProductsService.updateTotalCount(mappedStoreProduct.getId(), totalCount);
    }

    public void modifyOrder(int userId, int id, Orders order) {
        Optional<Orders> result = ordersRepository.findById(id);
        if (result.isEmpty())
            throw new ItemNotFoundException("Order: " + id + " does not exist");

        Orders oldOrder = result.get();
        if(oldOrder.getUser().getId() != userId)
            throw new RuntimeException("Access Denied");

        ordersRepository.save(order);
    }
}
