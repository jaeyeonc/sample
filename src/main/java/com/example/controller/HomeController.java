package com.example.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}