package com.green.namu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLoginRes {
    private final String accessToken;
    private final String refreshToken;
}
