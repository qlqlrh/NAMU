package com.green.namu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuSaveResponse {

    private String status;
    private String message;
    private String createdAt;

}
