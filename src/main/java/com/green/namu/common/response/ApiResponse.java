package com.green.namu.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
    private final boolean isSuccess;
    private final String message;
    private final Object data;

    public ApiResponse(BaseResponseStatus status, Object data) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.data = data;
    }
}
