package com.green.namu.service;

import com.green.namu.domain.Menu;
import com.green.namu.dto.AddMenuRequest;
import com.green.namu.dto.MenuSaveResponse;
import com.green.namu.exception.DuplicateSetNameException;
import com.green.namu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor // final이나 NotNull 필드의 생성자 추가
@Service // 빈으로 등록
public class MenuService {

    private final MenuRepository menuRepository;


    // 메뉴 추가 메서드
    public MenuSaveResponse save(AddMenuRequest request) {

        // 세트명이 이미 존재하는지 확인
        if (menuRepository.existsBySetName(request.getSetName())) {
            throw new DuplicateSetNameException("이미 존재하는 세트입니다.");
        }

        Menu savedMenu = menuRepository.save(request.toEntity());// DTO->Entity로 바꿔서 DB에 저장

        // 응답 메시지 구성
        return new MenuSaveResponse(
                savedMenu.getStatus().name(), // Enum의 이름을 문자열로 변환해서 반환
                "메뉴 등록이 완료 되었습니다.",
                savedMenu.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) // ISO 8601 형식 (년-월-일T시:분:초Z)
        );
    }
}