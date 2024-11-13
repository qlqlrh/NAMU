package com.green.namu.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 자동으로 생성
//@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자를 자동 생성
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
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

    @Column(name = "popularity", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean popularity = false;

    @Column(name = "menu_detail")
    private String menuDetail;

    @Column(name = "menu_category", nullable = false)
    private String menuCategory;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING) // enum의 값을 문자열 형태로 데이터베이스에 저장 (인덱스 형태가 아니라)
    private MenuStatus status = MenuStatus.ON_SALE;
}
