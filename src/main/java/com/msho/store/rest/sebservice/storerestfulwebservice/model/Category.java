package com.msho.store.rest.sebservice.storerestfulwebservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
