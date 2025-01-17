package com.green.namu.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    private String storeName;

    @Enumerated(EnumType.STRING)
    private StoreCategory storeCategory;

    private boolean isOpen;

    private String pickupTimes;

    private int minPrice;

    private int reviewCount;

    private int orderCount;

    private double storeRating;

    private int location;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SetName> setNames;

    public String getStoreCategoryName() {
        return this.storeCategory.name();
    }
}
