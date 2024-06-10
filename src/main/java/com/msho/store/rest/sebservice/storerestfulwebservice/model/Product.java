package com.msho.store.rest.sebservice.storerestfulwebservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private int ID;

    @JsonProperty("product-name")
    @Size(min=3, message = "Product's name should have at least 3 characters")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    private double fee;

    public Product() {
    }

    public Product(int ID, String name, Category category, double fee) {
        this.ID = ID;
        this.name = name;
        this.category = category;
        this.fee = fee;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
