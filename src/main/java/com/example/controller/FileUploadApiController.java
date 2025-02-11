package com.example.controller;

import com.example.common.UserDataValidationWrapper;
import com.example.common.ValidationError;
import com.example.common.ValidationResult;
import com.example.domain.UserData;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api")
public class FileUploadApiController {

    @PostMapping("/upload")
    public ResponseEntity<List<UserDataValidationWrapper>> handleUpload(@RequestParam("file") MultipartFile file) {
        List<UserDataValidationWrapper> userList = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            int rowCount = sheet.getLastRowNum(); // 실제 row 개수 확인

            // 헤더 중복 확인
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                ValidationResult validationResult = new ValidationResult();
                validationResult.addError(ValidationError.REQUIRED_FIELD, "Header Row");
                userList.add(new UserDataValidationWrapper(null, validationResult));
                return ResponseEntity.status(400).body(userList);
            }
            ValidationResult headerValidationResult = validateHeaders(headerRow);
            if (!headerValidationResult.isValid()) {
                userList.add(new UserDataValidationWrapper(null, headerValidationResult));
                return ResponseEntity.status(400).body(userList); // 헤더 중복 에러 반환
            }

            // Row 개수가 1001을 초과하는 경우 에러 처리
            if (rowCount > 1000) {
                ValidationResult validationResult = new ValidationResult();
                validationResult.addError(ValidationError.MAX_LENGTH_EXCEEDED, "Sheet Row Count");
                userList.add(new UserDataValidationWrapper(null, validationResult));
                return ResponseEntity.ok(userList); // 400 Bad Request 반환
            }

            // 중복 체크를 위한 기준 데이터를 저장할 Set 추가
            List<String> existingKeys = new ArrayList<>(); // 중복 체크를 위한 키 값 저장
            List<UserDataValidationWrapper> filteredList = new ArrayList<>();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;

                UserData user = new UserData();
                ValidationResult validationResult = validateRow(row, user);

                // rowIndex 값을 UserData의 row 필드에 설정
                user.setRow(String.valueOf(rowIndex)); //

                String uniqueKey = createUniqueKey(user);
                System.out.println("[DEBUG] Row Index: " + rowIndex + ", Unique Key: " + uniqueKey);

                if (existingKeys.contains(uniqueKey)) {
                    validationResult.addError(ValidationError.DUPLICATE_ENTRY, uniqueKey);
                    System.out.println("[DUPLICATE DETECTED] Unique Key: " + uniqueKey);
                } else {
                    existingKeys.add(uniqueKey);
                    System.out.println("[NEW KEY ADDED] Unique Key: " + uniqueKey);
                }

                filteredList.add(new UserDataValidationWrapper(user, validationResult));
            }

            userList.addAll(filteredList);

        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그 추가
            return ResponseEntity.status(400).body(userList);
        }
        return ResponseEntity.ok(userList);
    }

    private ValidationResult validateRow(Row row, UserData user) {
        ValidationResult validationResult = new ValidationResult();
        validateField(row, 0, "First Name", 40, true, validationResult, user::setFirstName);
        validateField(row, 1, "Last Name", 40, true, validationResult, user::setLastName);
        validateFieldWithPattern(row, 2, "PTN/Telephony Number", "\\d{1,20}", true, validationResult, user::setPtnOrTelephonyNumber);
        validateField(row, 3, "ID Number", 20, false, validationResult, user::setIdNumber);
        validateField(row, 4, "Position", 40, false, validationResult, user::setPosition);
        validateFieldWithPattern(row, 5, "Short Dialling Code", "\\d{1,20}", false, validationResult, user::setShortDiallingCode);
        validateField(row, 6, "Operational Command Unit", 40, false, validationResult, user::setOperationalCommandUnit);
        validateField(row, 7, "Call Sign", 25, false, validationResult, user::setCallSign);
        validateField(row, 8, "Organisation", 40, false, validationResult, user::setOrganisation);
        return validationResult;
    }

    private void validateField(Row row, int cellIndex, String fieldName, int maxLength, boolean required,
                               ValidationResult validationResult, java.util.function.Consumer<String> setter) {
        String cellValue = getCellValue(row, cellIndex);
        setter.accept(cellValue);
        if (required && (cellValue == null || cellValue.isEmpty())) {
            validationResult.addError(ValidationError.REQUIRED_FIELD, fieldName);
        } else if (cellValue != null && cellValue.length() > maxLength) {
            validationResult.addError(ValidationError.MAX_LENGTH_EXCEEDED, fieldName);
        }
    }

    private void validateFieldWithPattern(Row row, int cellIndex, String fieldName, String pattern, boolean required,
                                          ValidationResult validationResult, Consumer<String> setter) {
        String cellValue = getCellValue(row, cellIndex);
        setter.accept(cellValue);
        if (required && (cellValue == null || cellValue.isEmpty())) {
            validationResult.addError(ValidationError.REQUIRED_FIELD, fieldName);
        } else if (cellValue != null && !cellValue.matches(pattern)) {
            validationResult.addError(ValidationError.INVALID_FORMAT, fieldName);
        }
    }

    private String getCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) return null;
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    // Row 데이터에서 중복 체크를 위한 고유 키 생성 메서드 (First Name, Last Name, PTN/Telephony Number 사용)
    private String createUniqueKey(UserData user) {
        return String.format("%s|%s|%s",
                user.getFirstName() != null ? user.getFirstName() : "",
                user.getLastName() != null ? user.getLastName() : "",
                user.getPtnOrTelephonyNumber() != null ? user.getPtnOrTelephonyNumber() : ""
        ).toLowerCase().trim();
    }

    private ValidationResult validateHeaders(Row headerRow) {
        ValidationResult validationResult = new ValidationResult();
        Set<String> headerSet = new HashSet<>();
        for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
            String headerValue = getCellValue(headerRow, cellIndex);
            if (headerSet.contains(headerValue)) { // 중복된 헤더 확인
                validationResult.addError(ValidationError.DUPLICATE_ENTRY, "Header: " + headerValue);
            } else {
                headerSet.add(headerValue);
            }
        }
        return validationResult;
    }

}