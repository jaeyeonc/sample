package com.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/files")
public class FileDownloadController {

    // 엑셀 파일이 저장된 디렉토리 경로 (예: 로컬 파일 시스템)
    private static final String FILE_DIRECTORY = "files/";

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadExcelFile(@RequestParam("fileName") String fileName) {
        try {
            // resources/files 경로에서 파일을 찾음
            Resource resource = new ClassPathResource(FILE_DIRECTORY + fileName);

            // 파일이 존재하지 않는 경우 처리
            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Content-Disposition 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}