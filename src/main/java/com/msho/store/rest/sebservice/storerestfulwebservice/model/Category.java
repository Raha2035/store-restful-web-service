package com.msho.store.rest.sebservice.storerestfulwebservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private int ID;

    @JsonProperty("category-name")
    @Size(min=3, message = "Category's name should have at least 3 characters")
    private String name;

    @JsonProperty("category-value")
    private byte value;

    private byte available;

    @OneToMany (mappedBy = "category")
    @JsonIgnore
    private List<Product> products;

    public Category() {
    }

    public Category(int ID, String name, byte value, byte available) {
        this.ID = ID;
        this.name = name;
        this.value = value;
        this.available = available;
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

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public byte getAvailable() {
        return available;
    }

    public void setAvailable(byte available) {
        this.available = available;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", available=" + available +
                '}';
    }
}
