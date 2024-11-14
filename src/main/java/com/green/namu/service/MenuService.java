package com.green.namu.service;

import com.green.namu.domain.Menu;
import com.green.namu.dto.AddMenuRequest;
import com.green.namu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final이나 NotNull 필드의 생성자 추가
@Service // 빈으로 등록
public class MenuService {

    private final MenuRepository menuRepository;


    // 메뉴 추가 메서드
    public Menu save(AddMenuRequest request) {
        return menuRepository.save(request.toEntity()); // DTO->Entity로 바꿔서 DB에 저장
    }

}
