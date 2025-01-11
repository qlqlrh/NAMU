package com.green.namu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLoginRes {
    private final String accessToken;
    private final String refreshToken;
}
