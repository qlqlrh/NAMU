package com.green.namu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 글로벌 예외 처리
    // 유효성 검사를 통과하지 못한 경우 원하는 형식의 JSON 응답을 반환하도록 설정

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Invalid input");
        errors.put("message", "유효하지 않은 입력값입니다.");

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateSetNameException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateSetNameException(DuplicateSetNameException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Conflict");
        errors.put("message", exception.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
}
