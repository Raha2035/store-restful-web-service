package com.msho.store.rest.sebservice.storerestfulwebservice.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stores_products", uniqueConstraints={
        @UniqueConstraint(columnNames = {"store", "product", "total_count"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    @Column(name = "total_count")
    private int totalCount;
}
