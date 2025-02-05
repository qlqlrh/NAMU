package com.green.namu.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
    @JsonProperty("isSuccess")
    private final boolean isSuccess;
    private final String message;
    private final Object data;

    @JsonIgnore
    public boolean isSuccess() {
        return isSuccess;
    }

    public ApiResponse(BaseResponseStatus status, Object data) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.data = data;
    }
}
