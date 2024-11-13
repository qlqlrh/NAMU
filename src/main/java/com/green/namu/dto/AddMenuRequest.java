package com.green.namu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddMenuRequest { // 컨트롤러에서 요청한 본문을 받아올 객체 (DTO: Data Transfer Object)

    @JsonProperty("set_name") // JSON 요청에서 set_name이라는 필드가 들어오면, Jackson이 이를 자바 필드 setName에 매핑해줌
    private String setName;

    @JsonProperty("menu_names")
    private String menuNames;

    @JsonProperty("menu_price")
    private Integer menuPrice;

    @JsonProperty("menu_discount_price")
    private Integer menuDiscountPrice;

    @JsonProperty("menu_picture_url")
    private String menuPictureUrl;

    private Boolean popularity;

    @JsonProperty("menu_detail")
    private String menuDetail;
}
