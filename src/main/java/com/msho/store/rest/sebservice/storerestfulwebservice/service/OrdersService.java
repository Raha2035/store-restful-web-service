package com.msho.store.rest.sebservice.storerestfulwebservice.service;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.*;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    private final UserRepository userRepository;
    private final OrdersRepository ordersRepository;

    private final StoreRepository storeRepository;

    private final ProductRepository productRepository;

    private final StoresProductsRepository storesProductsRepository;

    public OrdersService(UserRepository userRepository,
                         OrdersRepository ordersRepository,
                         StoreRepository storeRepository,
                         ProductRepository productRepository,
                         StoresProductsRepository storesProductsRepository) {
        this.userRepository = userRepository;
        this.ordersRepository = ordersRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.storesProductsRepository = storesProductsRepository;
    }

    private User getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new RuntimeException("User doesn't exist");
        }

        return user.get();
    }

    private Store getStoreById(int id){
        Optional<Store> store = storeRepository.findById(id);
        if(store.isEmpty())
            throw new RuntimeException("Store doesn't exist");

        return store.get();
    }

    private Product getProductById(int id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty())
            throw new RuntimeException("Product doesn't exist");

        return product.get();
    }

    private StoresProducts getStoresProductsByStoreAndProduct(Store store, Product product){
        Optional<StoresProducts> storesProducts = storesProductsRepository.findByStoreAndProduct(store, product);
        if(storesProducts.isEmpty() || storesProducts.get().getTotalCount() == 0)
            throw new RuntimeException("This Store doesn't have this Product");

        return storesProducts.get();
    }

    public List<Orders> findAllOrdersOfOneUser(int userId){
        User user = this.getUserById(userId);
        return ordersRepository.findAllByUser(user);
    }

    @Transactional
    public Orders createOrderForOneUser(int userId, Orders orders){
        User user = this.getUserById(userId);

        Store store = this.getStoreById(orders.getStore().getID());

        Product product = this.getProductById(orders.getProduct().getID());

        StoresProducts storesProducts = getStoresProductsByStoreAndProduct(store, product);
        int totalCount = storesProducts.getTotalCount();

        if(orders.getNumber() > totalCount || totalCount == 0)
            throw new RuntimeException("Not enough inventory");

        double totalPrice = orders.getNumber() * product.getFee();

        orders.setUser(user);
        orders.setStore(store);
        orders.setProduct(product);
        orders.setTotalPrice(totalPrice);

        Orders savedOrder = ordersRepository.findByUserAndStoreAndProduct(user, store, product);

        if( savedOrder != null)
            ordersRepository.updateByUserAndStoreAndProduct(user, store, product, orders.getNumber());
        else
            savedOrder = ordersRepository.save(orders);

        int count = totalCount - savedOrder.getNumber();
        storesProductsRepository.updateTotalCount(storesProducts.getId(), count);

        return savedOrder;
    }

    @Transactional
    public void deleteOrderById(int userId, int id){
        Optional<Orders> result = ordersRepository.findById(id);
        if (result.isEmpty())
            throw new RuntimeException("Order: " + id + " does not exist");

        Orders order = result.get();
        if(order.getUser().getID() != userId)
            throw new RuntimeException("Access Denied");
        int numberOfOrderProduct = order.getNumber();

        ordersRepository.deleteById(id);
        Optional<StoresProducts> mappedStoreProduct = storesProductsRepository.findByStoreAndProduct(order.getStore(), order.getProduct());
        int inventory = mappedStoreProduct.get().getTotalCount();

        int totalCount = inventory + numberOfOrderProduct;

        storesProductsRepository.updateTotalCount(mappedStoreProduct.get().getId(), totalCount);
    }

    public void modifyOrder(int userId, int id, Orders order) {
        Optional<Orders> result = ordersRepository.findById(id);
        if (result.isEmpty())
            throw new RuntimeException("Order: " + id + " does not exist");

        Orders oldOrder = result.get();
        if(oldOrder.getUser().getID() != userId)
            throw new RuntimeException("Access Denied");

        ordersRepository.save(order);
    }
}
