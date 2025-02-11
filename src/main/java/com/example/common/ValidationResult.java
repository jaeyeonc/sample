package com.example.common;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private boolean valid = true;
    private final List<String> errors = new ArrayList<>();

    public void addError(ValidationError errorType, String fieldName) {
        valid = false; // 한 번이라도 에러가 발생하면 유효성 검증 실패로 설정
        switch (errorType) {
            case REQUIRED_FIELD:
                errors.add(fieldName + " is required.");
                break;
            case MAX_LENGTH_EXCEEDED:
                errors.add(fieldName + " exceeds the maximum allowed length.");
                break;
            case INVALID_FORMAT:
                errors.add(fieldName + " has an invalid format.");
                break;
            case DUPLICATE_ENTRY: // 중복 항목 에러 처리 추가
                errors.add("Duplicate entry found for " + fieldName + ".");
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