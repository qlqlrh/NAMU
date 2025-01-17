package com.green.namu.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, "요청에 성공하였습니다", null),
    CART_ADDED_SUCCESS(true, "장바구니에 담겼습니다.", null),

    /**
     * 400 : Request, Response 오류
     */
    STORE_NOT_FOUND(false, "해당 Store 데이터를 찾을 수 없습니다.", "STORE_NOT_FOUND"),
    MENU_NOT_FOUND(false, "해당 Menu 데이터를 찾을 수 없습니다.", "MENU_NOT_FOUND"),
    LIKE_NOT_FOUND(false, "삭제할 찜 데이터를 찾을 수 없습니다.", "LIKE_NOT_FOUND"),
    USER_OR_MENU_NOT_FOUND(false, "사용자 또는 메뉴를 찾을 수 없습니다.", "USER_OR_MENU_NOT_FOUND"),
    CART_ITEM_NOT_FOUND(false, "수정할 장바구니 데이터를 찾을 수 없습니다.", "CART_ITEM_NOT_FOUND"),


    SEARCH_NO_RESULTS(false, "검색 결과가 존재하지 않습니다.", "SEARCH_NO_RESULTS"),

    INVALID_SEARCH_QUERY(false, "검색어를 입력해주세요.", "INVALID_SEARCH_QUERY"),
    INVALID_SORT_OPTION(false, "유효하지 않은 정렬 옵션입니다. 'normal', 'like', 'distance', 'rating', 'price' 중 하나를 사용하세요.", "INVALID_SORT_OPTION"),
    INVALID_LIKE_SORT_OPTION(false, "유효하지 않은 정렬 옵션입니다. 'normal', 'often', 'abc' 중 하나를 사용하세요.", "INVALID_LIKE_SORT_OPTION"),
    INVALID_STORE_ID(false, "유효하지 않은 가게 ID입니다.", "INVALID_STORE_ID"),
    INVALID_REQUEST_DATA(false, "유효하지 않은 요청 데이터입니다.", "INVALID_REQUEST_DATA"),
    INVALID_USER_ID(false, "유효하지 않은 사용자 ID입니다.", "INVALID_USER_ID"),

    AUTHENTICATION_FAILED(false, "인증 정보가 유효하지 않습니다. 다시 로그인해주세요.", "AUTHENTICATION_FAILED"),
    FORBIDDEN_ACCESS(false, "해당 찜 데이터를 삭제할 권한이 없습니다.", "FORBIDDEN_ACCESS"),
    FORBIDDEN_ACCESS_MYPAGE(false, "해당 사용자 정보를 조회할 권한이 없습니다.", "FORBIDDEN_ACCESS_MYPAGE"),


    ALREADY_LIKED_STORE(false, "이미 찜한 가게입니다.", "ALREADY_LIKED_STORE"),

    CART_EMPTY(false, "장바구니에 상품이 없습니다.", "CART_EMPTY"),
    INSUFFICIENT_STOCK(false, "요청한 수량만큼의 재고가 부족합니다.", "INSUFFICIENT_STOCK"),

    MISSING_REQUIRED_FIELD(false, "Required field 'user_id' is missing", "MISSING_REQUIRED_FIELD"),

    EMPTY_JWT(false, "JWT를 입력해주세요.", "EMPTY_JWT"),
    INVALID_JWT(false, "유효하지 않은 JWT입니다.", "INVALID_JWT"),



    /**
     * 500 :  Database, Server 오류
     */
    INTERNAL_SERVER_ERROR(false, "서버 내부 오류로 인해 데이터를 가져올 수 없습니다. 잠시 후 다시 시도해주세요.",  "INTERNAL_SERVER_ERROR");


    private final boolean isSuccess;
    private final String message;
    private final String errorCode;

    private BaseResponseStatus(boolean isSuccess, String message, String errorCode) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.errorCode = errorCode;
    }
}
