package com.example.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        // 세션에서 사용자 정보 확인
        String username = (String) session.getAttribute("USER");

        if (username == null) {
            // 세션 데이터가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 세션 데이터가 존재하면 홈 페이지로 이동
        model.addAttribute("username", username);
        return "home"; // 홈 페이지 템플릿
    }

    // HTML 페이지 반환 (다운로드 버튼이 있는 페이지)
    @GetMapping("/download-page")
    public String downloadPage() {
        return "download";  // "downloadPage.html" 파일을 반환
    }

    // 경로에 있는 파일을 다운로드하는 엔드포인트
    @GetMapping("/download-file")
    public ResponseEntity<FileSystemResource> downloadFile() throws IOException {
        // 서버의 경로에 있는 엑셀 파일 지정
        String filePath = "C:\\workspace\\sample3\\src\\main\\resources\\sampleCsv.csv";  // 실제 파일 경로로 변경

        // 해당 파일이 존재하는지 확인
        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();  // 파일이 없으면 404 응답
        }

        // FileSystemResource로 파일을 리턴
        FileSystemResource resource = new FileSystemResource(file);

        // 파일 다운로드 헤더 설정
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .body(resource);
    }

}