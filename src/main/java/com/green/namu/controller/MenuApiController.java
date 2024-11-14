package com.green.namu.controller;

import com.green.namu.domain.Menu;
import com.green.namu.dto.AddMenuRequest;
import com.green.namu.dto.MenuSaveResponse;
import com.green.namu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // HTTP ResponseBody에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class MenuApiController {

    private final MenuService menuService;

    @PostMapping("/menu/save")
    public ResponseEntity<MenuSaveResponse> addMenu(@RequestBody AddMenuRequest request) {
        MenuSaveResponse response = menuService.save(request);

        // 요청한 자원이 성공적으로 생성되었음과 응답 객체를 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}