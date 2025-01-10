package com.example.controller;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class ExampleController {
    private static final String INDEX_VIEW = "index";
    private static final String UPLOAD_VIEW = "upload";
    private static final String EXCEL_MIME_XLS = "application/vnd.ms-excel";
    private static final String EXCEL_MIME_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String CSV_MIME = "text/csv";

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/index")
    public String examplePage() {
        return INDEX_VIEW;
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return UPLOAD_VIEW;
    }

    @PostMapping("/upload")
    public String handleFileUpload(MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", getLocalizedMessage("file.empty"));
            return UPLOAD_VIEW;
        }

        if (!isValidExcelFile(file.getContentType())) {
            model.addAttribute("message", "엑셀 파일만 업로드할 수 있습니다.");
            return UPLOAD_VIEW;
        }

        try (InputStream inputStream = file.getInputStream()) {
            List<List<String>> excelData = extractExcelData(inputStream);
            model.addAttribute("data", excelData);
            model.addAttribute("message", getLocalizedMessage("file.upload.success"));
        } catch (IOException e) {
            model.addAttribute("message", getLocalizedMessage("file.upload.error"));
        } catch (Exception e) {
            model.addAttribute("message", getLocalizedMessage("file.upload.error") + e.getMessage());
        }
        return UPLOAD_VIEW;
    }

    private String getLocalizedMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }

    private boolean isValidExcelFile(String contentType) {
        return EXCEL_MIME_XLS.equals(contentType) || EXCEL_MIME_XLSX.equals(contentType) || CSV_MIME.equals(contentType);
    }

    private List<List<String>> extractExcelData(InputStream inputStream) throws IOException {
        List<List<String>> excelData = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(getCellValue(cell));
            }
            excelData.add(rowData);
        }
        return excelData;
    }

    private String getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }
}