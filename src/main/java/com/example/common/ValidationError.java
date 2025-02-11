package com.example.common;

public enum ValidationError {
    REQUIRED_FIELD("REQUIRED_FIELD", "필수 입력 항목입니다."),
    MAX_LENGTH_EXCEEDED("MAX_LENGTH_EXCEEDED", "최대 길이를 초과했습니다."),
    INVALID_FORMAT("INVALID_FORMAT", "유효한 형식이 아닙니다."),
    DUPLICATE_HEADER("DUPLICATE_HEADER", "중복된 헤더가 있습니다."),
    UNSUPPORTED_CHARACTERS("UNSUPPORTED_CHARACTERS", "허용되지 않은 문자가 포함되어 있습니다."),
    DUPLICATE_ENTRY("DUPLICATE_ENTRY", "Duplicate entry found");

    private final String code;
    private final String message;

    ValidationError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}