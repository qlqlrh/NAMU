package com.green.namu.domain;

import com.green.namu.common.entity.BaseEntity;
import com.green.namu.domain.status.MenuStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "set_name", nullable = false)
    private String setName;

    @Column(name = "menu_names", nullable = false)
    private String menuNames;

    @Column(name = "menu_price", nullable = false)
    private Integer menuPrice;

    @Column(name = "menu_discount_price", nullable = false)
    private Integer menuDiscountPrice;

    @Column(name = "menu_picture_url")
    private String menuPictureUrl;

    @Builder.Default
    @Column(name = "popularity")
    private Boolean popularity = false; // 수정된 부분

    @Column(name = "menu_detail")
    private String menuDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_category", nullable = false)
    private Category menuCategory;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MenuStatus status = MenuStatus.ON_SALE; // 수정된 부분

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public void setStore(Store store) {
        this.store = store;
    }
}
