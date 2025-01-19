package com.green.namu.controller;

import com.green.namu.domain.OauthServerType;
import org.springframework.core.convert.converter.Converter;

public class OauthServerTypeConverter implements Converter<String, OauthServerType> {

    // String -> OauthServerType  변환
    @Override
    public OauthServerType convert(String source) {
        return OauthServerType.fromName(source);
    }
}
