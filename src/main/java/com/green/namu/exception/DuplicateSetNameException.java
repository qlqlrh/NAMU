package com.green.namu.exception;

public class DuplicateSetNameException extends RuntimeException {
    public DuplicateSetNameException(String massage) {
        super(massage);
    }
}
