package com.green.namu.controller;

import com.green.namu.common.exceptions.BaseException;
import com.green.namu.common.response.BaseResponse;
import com.green.namu.common.response.BaseResponseStatus;
import com.green.namu.dto.StoreSearchRes;
import com.green.namu.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.green.namu.dto.StoreResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequiredArgsConstructor
@Tag(name = "가게", description = "가게 관련 API")
public class StoreController {

    private final StoreService storeService;

    @Operation(summary = "전체 가게 조회", description = "등록된 전체 가게를 조회합니다.")
    @GetMapping("/store/list")
    public List<StoreResponseDto> getAllStores() {
        return storeService.getAllStoresWithMenus();
    }

    @Operation(summary = "특정 가게 조회", description = "특정 가게 하나만을 조회합니다.")
    @GetMapping("/store/{storeId}")
    public StoreResponseDto getStoreById(@PathVariable Long storeId) {
        return storeService.getStoreByIdWithMenus(storeId);
    }

    @Operation(summary = "가게 검색", description = "카테고리, 가게 이름, 메뉴 이름, 세트 이름을 입력받아 검색 결과(가게리스트)를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "SEARCH_NO_RESULTS"),
            @ApiResponse(responseCode = "400", description = "INVALID_SEARCH_QUERY"),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR"),
    })
    @GetMapping("/search")
    public BaseResponse<List<StoreSearchRes>> searchStores(
            @RequestParam(value = "term", required = false) String term,
            @RequestParam(value = "sort", defaultValue = "normal") String option) {

        // validation
        if (term == null || term.isEmpty()) {
            throw new BaseException(BaseResponseStatus.INVALID_SEARCH_QUERY);
        }

        List<String> validOptions = List.of("normal", "rating", "distance", "like", "order", "price");
        if (!validOptions.contains(option)) {
            throw new BaseException(BaseResponseStatus.INVALID_SORT_OPTION);
        }

        try {
            List<StoreSearchRes> searchResults = storeService.searchStores(term, option);
            return new BaseResponse<>(searchResults);
        } catch (BaseException e) {
            log.error("검색 중 오류 발생: ", e);
            return new BaseResponse<>(e.getStatus());
        }
    }
}