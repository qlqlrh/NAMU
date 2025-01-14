package com.green.namu.controller;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponse;
import com.green.namu.domain.Menu;
import com.green.namu.dto.AddMenuRequest;
import com.green.namu.dto.MenuReadResponse;
import com.green.namu.dto.MenuSaveResponse;
import com.green.namu.service.MenuService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.hibernate.query.sqm.tree.SqmNode.log;


@RequiredArgsConstructor
@RestController // HTTP ResponseBody에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class MenuApiController {

    private final MenuService menuService;

    @PostMapping("/menu/save")
    public ResponseEntity<MenuSaveResponse> addMenu(@RequestBody @Valid AddMenuRequest request) {
        MenuSaveResponse response = menuService.save(request);

        // 요청한 자원이 성공적으로 생성되었음과 응답 객체를 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping("/menu/{menuId}")
    public BaseResponse<MenuReadResponse> getMenuById(@PathVariable("menuId") Long menuId) {
        try {
            MenuReadResponse response = menuService.findById(menuId);
            return new BaseResponse<>(response);
        } catch (BaseException e) { // menuService.findById 매서드에서 던진 에러 catch
            log.error("메뉴 조회 중 오류 발생: ", e);
            return new BaseResponse<>(e.getStatus());
        }
    }
}