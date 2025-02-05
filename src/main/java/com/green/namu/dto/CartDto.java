package com.green.namu.dto;

import com.green.namu.domain.status.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private int cartQuantity;
    private String request;
    private Status status;
    private Long menuId;
}
