package org.example.linfinityfirst.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 화면(HTML)을 리턴하는 컨트롤러
public class PageController {

    // 메인 페이지
    @GetMapping("/")
    public String home() {
        return "index"; // templates/index.html
    }

    // 로그인 페이지 보여주기
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }

    // 회원가입 페이지 보여주기
    @GetMapping("/signup")
    public String signupPage() {
        return "signup"; // templates/signup.html
    }

    // Qna 글쓰기 페이지
    // SecurityConfig 설정에 의해, 로그인 안 한 사람은 여기 못 들어오고 로그인 페이지로 튕겨짐
    @GetMapping("/qna/write")
    public String qnaWritePage() {
        return "qna/write"; // templates/qna/write.html
    }
}