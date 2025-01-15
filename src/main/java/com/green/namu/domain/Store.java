package com.green.namu.domain;

import com.green.namu.common.converter.JpaJsonConverter;
import com.green.namu.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "store")
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_category", nullable = false)
    private Category storeCategory;

    @Column(name = "store_address", nullable = false)
    private String storeAddress;

    @Convert(converter = JpaJsonConverter.class) // JSON을 변환하는 Converter 설정
    @Column(name = "store_picture_urls", columnDefinition = "JSON")
    private List<String> storePictureUrls;

    @Column(name = "store_phone", nullable = false, length = 20)
    private String storePhone;

    @Column(name = "notice", columnDefinition = "TEXT")
    private String notice;

    @Column(name = "store_content", columnDefinition = "TEXT")
    private String storeContent;

    @Column(name = "pickup_time", nullable = false)
    private String pickupTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "closed_days")
    private ClosedDays closedDays;

    @Column(name = "store_rating", nullable = false)
    @ColumnDefault("0")
    private int storeRating;

    @Column(name = "favorite_count", nullable = false)
    @ColumnDefault("0")
    private int favoriteCount;

    @Column(name = "review_count", nullable = false)
    @ColumnDefault("0")
    private int reviewCount;

    @Column(name = "order_count", nullable = false)
    @ColumnDefault("0")
    private int orderCount;

    @Column(name = "ceo_name", nullable = false, length = 20)
    private String ceoName;

    @Column(name = "company_name", nullable = false, length = 20)
    private String companyName;

    @Column(name = "company_address", nullable = false)
    private String companyAddress;

    @Column(name = "business_number", nullable = false, length = 12)
    private String businessNumber;

    @Column(name = "country_of_origin", columnDefinition = "TEXT", nullable = false)
    private String countryOfOrigin;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @ColumnDefault("'ACTIVE'")
    private Status status;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    public void addMenu(Menu menu) {
        this.menus.add(menu);
        menu.setStore(this);
    }

    // ENUM for closed_days
    public enum ClosedDays {
        MON, TUE, WED, THU, FRI, SAT, SUN
    }
}
