package org.example.linfinityfirst.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    @GetMapping("/qna/write")
    public String qnaWritePage() {
        return "qna/write"; // templates/qna/write.html
    }

    //Qna 목록 페이지
    @GetMapping("/qna")
    public String qnaListPage() {
        return "qna/list"; // templates/qna/list.html
    }

    //Qna 상세 페이지
    @GetMapping("/qna/{id}")
    public String qnaDetailPage(@PathVariable Long id) {
        return "qna/detail"; // templates/qna/detail.html
    }
}