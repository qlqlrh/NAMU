package com.green.namu.service;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.domain.Menu;
import com.green.namu.dto.AddMenuReq;
import com.green.namu.dto.MenuReadRes;
import com.green.namu.dto.MenuSaveRes;
import com.green.namu.exception.DuplicateSetNameException;
import com.green.namu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final이나 NotNull 필드의 생성자 추가
@Service // 빈으로 등록
public class MenuService {

    private final MenuRepository menuRepository;


    // 메뉴 추가 메서드
    public MenuSaveRes save(AddMenuReq request) {

        // 세트명이 이미 존재하는지 확인
        if (menuRepository.existsBySetName(request.getSetName())) {
            throw new DuplicateSetNameException("이미 존재하는 세트입니다.");
        }

        Menu savedMenu = menuRepository.save(request.toEntity());// DTO->Entity로 바꿔서 DB에 저장

        // 응답 메시지 구성
        return new MenuSaveRes(
                savedMenu.getStatus().name(), // Enum의 이름을 문자열로 변환해서 반환
                "메뉴 등록이 완료 되었습니다.");
    }

    // 특정 메뉴 조회
    public MenuReadRes findById(Long id) {

        // 존재하지 않는 메뉴 ID일 경우 예외 처리
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.MENU_NOT_FOUND));

        return new MenuReadRes(
                menu.getSetName(),
                menu.getMenuNames(),
                menu.getMenuPrice(),
                menu.getMenuDiscountPrice(),
                menu.getMenuPictureUrl(),
                menu.getPopularity(),
                menu.getMenuDetail()
        );
    }
}
