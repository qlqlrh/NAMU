package com.green.namu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.namu.domain.Menu;
import com.green.namu.dto.AddMenuRequest;
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
        final AddMenuRequest userRequest = new AddMenuRequest(setName, menuNames, menuPrice, menuDiscountPrice, menuPictureUrl, popularity, menuDetail, menuCategory);

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
}