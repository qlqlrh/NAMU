package com.green.namu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.namu.domain.Category;
import com.green.namu.domain.Menu;
import com.green.namu.domain.status.MenuStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddMenuRequest {
    // 컨트롤러에서 요청한 본문을 받아올 객체 (DTO: Data Transfer Object)
    // status, id는 포함하지 않음

    @JsonProperty("set_name") // JSON 요청에서 set_name이라는 필드가 들어오면, Jackson이 이를 자바 필드 setName에 매핑해줌
    @NotBlank(message = "세트 이름은 필수 항목입니다.")
    private String setName;

    @JsonProperty("menu_names")
    @NotBlank(message = "메뉴 이름은 필수 항목입니다.")
    private String menuNames;

    @JsonProperty("menu_price")
    @NotNull(message = "메뉴 가격은 필수 항목입니다.")
    @Min(value = 0, message = "메뉴 가격은 0 이상이어야 합니다.")
    private Integer menuPrice;

    @JsonProperty("menu_discount_price")
    @NotNull(message = "할인 가격은 필수 항목입니다.")
    @Min(value = 0, message = "할인 가격은 0 이상이어야 합니다.")
    private Integer menuDiscountPrice;

    @JsonProperty("menu_picture_url")
    private String menuPictureUrl;

    private Boolean popularity; // 이걸 요청하는 클라이언트가 가게 사장님이면 DTO에 포함해야 하고, 고객이면 빼야할 듯함

    @JsonProperty("menu_detail")
    private String menuDetail;

    @JsonProperty("menu_category")
    private Category menuCategory;

    public Menu toEntity() {
        // DTO -> Entity 변환 메서드
        // 빌더 패턴은 null 값이 들어온 필드에 대해서는 값을 설정하지 않고 넘어감
        return Menu.builder()
                .setName(setName)
                .menuNames(menuNames)
                .menuPrice(menuPrice)
                .menuDiscountPrice(menuDiscountPrice)
                .menuPictureUrl(menuPictureUrl)
                .popularity(popularity)
                .menuDetail(menuDetail)
                .menuCategory(menuCategory)
                .status(MenuStatus.ON_SALE) // 기본값 설정 필요
                .build();
    }
}
