package com.example.common;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private boolean valid = true;
    private final List<String> errors = new ArrayList<>();

    public void addError(ValidationError errorType, String fieldName) {
        valid = false; // 한 번이라도 에러가 발생하면 유효성 검증 실패로 설정
        switch (errorType) {
            case REQUIRED_FIELD, MAX_LENGTH_EXCEEDED, INVALID_FORMAT, UNSUPPORTED_CHARACTERS, DUPLICATE_ENTRY:
                errors.add(errorType.getMessage() + " " + fieldName);
                break;
            default :
                errors.add(errorType.getMessage());
                break;
        }
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getErrors() {
        return errors;
    }
}