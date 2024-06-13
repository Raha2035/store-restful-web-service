package com.msho.store.rest.sebservice.storerestfulwebservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @JsonProperty("store-name")
    private String name;

    public Store() {
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


    @Override
    public String toString() {
        return "Store{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }
}
