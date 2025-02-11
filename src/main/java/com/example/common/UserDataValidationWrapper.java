package com.example.common;

import com.example.domain.UserData;

public class UserDataValidationWrapper {
    private UserData userData;
    private ValidationResult validationResult;

    public UserDataValidationWrapper(UserData userData, ValidationResult validationResult) {
        this.userData = userData;
        this.validationResult = validationResult;
    }

    public UserData getUserData() {
        return userData;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }
}