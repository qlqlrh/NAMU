package com.green.namu.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean isSuccess;
    private String message;
    private Object data;
}