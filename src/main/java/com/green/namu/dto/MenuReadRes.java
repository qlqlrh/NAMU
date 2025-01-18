package com.green.namu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuReadRes {

    @JsonProperty("set_name")
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
