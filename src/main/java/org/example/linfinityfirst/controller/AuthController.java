package org.example.linfinityfirst.controller;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.dto.request.LoginRequestDto;
import org.example.linfinityfirst.dto.request.SignupRequestDto;
import org.example.linfinityfirst.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // 회원가입 기능
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

}