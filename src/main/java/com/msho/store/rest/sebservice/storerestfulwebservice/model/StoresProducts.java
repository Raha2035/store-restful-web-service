package com.msho.store.rest.sebservice.storerestfulwebservice.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"store", "product", "totalCount"})
})
public class StoresProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    @JoinColumn(name = "store", referencedColumnName = "id")
    private Store store;

    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    @JoinColumn(name = "product", referencedColumnName = "id")
    private Product product;

    private int totalCount;

    public StoresProducts() {
    }

    public StoresProducts(int id, @Nullable Store store, @Nullable Product product, int totalCount) {
        this.id = id;
        this.store = store;
        this.product = product;
        this.totalCount = totalCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public Store getStore() {
        return store;
    }

    public void setStore(@Nullable Store store) {
        this.store = store;
    }

    @Nullable
    public Product getProduct() {
        return product;
    }

    public void setProduct(@Nullable Product product) {
        this.product = product;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "StoresProducts{" +
                "id=" + id +
                ", store=" + store +
                ", product=" + product +
                ", totalCount=" + totalCount +
                '}';
    }
}
