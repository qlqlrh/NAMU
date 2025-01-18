package com.green.namu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuSearchRes {
    @JsonProperty("set_name")
    private String setName;

    @JsonProperty("menu_names")
    private String menuNames;
}
