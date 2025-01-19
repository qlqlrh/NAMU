package com.green.namu.common.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

/**
 * JSON 데이터를 데이터베이스에 저장하거나 읽을 때 사용하는 변환기(Converter) 역할
 * JSON 데이터를 Java 객체와 데이터베이스 컬럼 간에 변환
 * Store 테이블의 store_picture_urls 필드에 값을 저장하거나 읽을 때 필요
 */
@Converter
public class JpaJsonConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            return attribute == null ? null : objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not convert list to JSON string.", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            return dbData == null ? null : objectMapper.readValue(dbData, List.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not convert JSON string to list.", e);
        }
    }
}
