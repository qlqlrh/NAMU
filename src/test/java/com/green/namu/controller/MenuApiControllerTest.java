package com.green.namu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.namu.domain.Menu;
<<<<<<< HEAD
import com.green.namu.domain.status.MenuStatus;
import com.green.namu.dto.AddMenuReq;
=======
>>>>>>> origin/master
import com.green.namu.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성
class MenuApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MenuRepository menuRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        menuRepository.deleteAll();
    }

    @DisplayName("addMenu: 메뉴 추가에 성공한다.")
    @Test
    public void addMenu() throws Exception {
        // given
        final String url = "/menu/save";
        final String setName = "세트A";
        final String menuNames = "애플 와플(1), 콘치폭 핫도그(1)";
        final Integer menuPrice = 10100;
        final Integer menuDiscountPrice = 5000;
        final String menuPictureUrl = "https://asdf.com";
        final Boolean popularity = true;
        final String menuDetail = "애플 와플: ~~, 콘치폭 핫도그: ~~";
        final String menuCategory = "베이커리";
<<<<<<< HEAD
        final AddMenuReq userRequest = new AddMenuReq(setName, menuNames, menuPrice, menuDiscountPrice, menuPictureUrl, popularity, menuDetail, menuCategory);
=======
        final AddMenuRequest userRequest = new AddMenuRequest(setName, menuNames, menuPrice, menuDiscountPrice, menuPictureUrl, popularity, menuDetail, menuCategory);
>>>>>>> origin/master

        // request 객체를 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Menu> menus = menuRepository.findAll();

        assertThat(menus.size()).isEqualTo(1); // 크기가 1인지 검증
        assertThat(menus.get(0).getSetName()).isEqualTo(setName);
        assertThat(menus.get(0).getMenuCategory()).isEqualTo(menuCategory);
    }

    @DisplayName("getMenuById: 특정 메뉴 ID로 메뉴 조회에 성공한다.")
    @Test
    public void getMenuById() throws Exception {
        // given
        final String url = "/menu/{menuId}";
        final String setName = "세트A";
        final String menuNames = "애플 와플(1), 콘치폭 핫도그(1)";
        final Integer menuPrice = 10100;
        final Integer menuDiscountPrice = 5000;
        final String menuPictureUrl = "https://asdf.com";
        final Boolean popularity = true;
        final String menuDetail = "애플 와플: ~~, 콘치폭 핫도그: ~~";
        final String menuCategory = "베이커리";

        // 메뉴 저장
        // * new Menu 형식이 아니라, 빌더 형식으로 만들어서 save 해야 Id랑 createdAt, updatedAt 필드를 비울 수 있음!
        Menu savedMenu = menuRepository.save(Menu.builder()
                        .setName(setName)
                        .menuNames(menuNames)
                        .menuPrice(menuPrice)
                        .menuDiscountPrice(menuDiscountPrice)
                        .menuPictureUrl(menuPictureUrl)
                        .popularity(popularity)
                        .menuDetail(menuDetail)
                        .menuCategory(menuCategory)
                        .status(MenuStatus.ON_SALE)
                        .build());

        // when
        ResultActions result = mockMvc.perform(get(url, savedMenu.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.set_name").value(setName))
                .andExpect(jsonPath("$.menu_names").value(menuNames))
                .andExpect(jsonPath("$.menu_price").value(menuPrice))
                .andExpect(jsonPath("$.menu_discount_price").value(menuDiscountPrice))
                .andExpect(jsonPath("$.menu_picture_url").value(menuPictureUrl))
                .andExpect(jsonPath("$.popularity").value(popularity));
    }

}