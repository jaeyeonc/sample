package com.example.common;

public enum ValidationError {
    //data valid 케이스
    REQUIRED_FIELD("REQUIRED_FIELD", "Missing"),
    MAX_LENGTH_EXCEEDED("MAX_LENGTH_EXCEEDED", "최대 길이를 초과했습니다."),
    INVALID_FORMAT("INVALID_FORMAT", "유효한 형식이 아닙니다."),
    UNSUPPORTED_CHARACTERS("UNSUPPORTED_CHARACTERS", "허용되지 않은 문자가 포함되어 있습니다."),
    DUPLICATE_ENTRY("DUPLICATE_ENTRY", "Duplicate entry found"),
    //file error 케이스
    OVER_DATA_FILE("OVER_DATA_FILE", "The file you uploaded contains more than 1000 administrators. " +
            "Select a file with 1000 or fewer ETC users and try again."),
    NO_DATA_FILE("NO_DATA_FILE", "The file you uploaded is missing some administrator information.\n" +
            "Make sure the information is accurate and try again."),
    INVALID_HEADER_FORMAT("INVALID_HEADER_FORMAT", "The file you uploaded contains unreadable, missing, " +
            "or duplicated column heading, Make sure the column headings match the template and try again.");

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