package com.msho.store.rest.sebservice.storerestfulwebservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user", "store", "product", "number"})
})
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "store", referencedColumnName = "id")
    private Store store;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "product", referencedColumnName = "id")
    private Product product;

    @Positive(message = "Number of product must have greater than 0")
    private int number;

   @JsonIgnore
    private double totalPrice;

    public Orders() {
    }

    public Orders(int id, User user, Store store, Product product, int number, double totalPrice) {
        this.id = id;
        this.user = user;
        this.store = store;
        this.product = product;
        this.number = number;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", user=" + user +
                ", store=" + store +
                ", product=" + product +
                ", number=" + number +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
