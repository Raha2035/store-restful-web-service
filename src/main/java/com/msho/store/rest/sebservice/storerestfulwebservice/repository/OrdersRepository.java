package com.msho.store.rest.sebservice.storerestfulwebservice.repository;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.Orders;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Product;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.Store;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findAllByUser(User user);

    Orders findByUserAndStoreAndProduct(User user, Store store, Product product);

    @Modifying
    @Query(value = "update Orders o set o.number = :number where o.user = :user and o.store = :store and o.product = :product")
    void updateByUserAndStoreAndProduct(@Param("user") User user, @Param("store") Store store, @Param("product") Product product, @Param("number") int number);
}
