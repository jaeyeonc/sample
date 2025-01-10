package com.example.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    // 1. 로그인 페이지 GET 요청
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // 로그인 폼 페이지
    }

    // 2. 로그인 POST 처리
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        // 간단한 인증 로직 (사용자 아이디와 비밀번호를 하드코딩)
        if ("user".equals(username) && "password".equals(password)) {
            // 인증 성공 시 세션에 사용자 정보 저장
            session.setAttribute("USER", username);
            return "redirect:/home"; // 로그인 성공 후 홈으로 리다이렉트
        }

        // 인증 실패 처리
        model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
        return "login";
    }

    // 3. 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/login";
    }
}