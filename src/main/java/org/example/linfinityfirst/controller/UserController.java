package org.example.linfinityfirst.controller;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.dto.response.NoticeResponseDto;
import org.example.linfinityfirst.dto.response.ProductResponseDto;
import org.example.linfinityfirst.dto.response.QnaResponseDto;
import org.example.linfinityfirst.dto.response.UserSummaryResponse;
import org.example.linfinityfirst.service.NoticeService;
import org.example.linfinityfirst.service.ProductService;
import org.example.linfinityfirst.service.QnaService;
import org.example.linfinityfirst.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final QnaService qnaService;
    private final NoticeService noticeService;
    private final ProductService productService;

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserSummaryResponse> getMyInfo(Principal principal) {
        return ResponseEntity.ok(userService.getMyInfo(principal.getName()));
    }

    // 내가 쓴 QnA 조회
    @GetMapping("/me/qna")
    public ResponseEntity<List<QnaResponseDto>> getMyQna(Principal principal) {
        return ResponseEntity.ok(qnaService.getMyQnaList(principal.getName()));
    }

    // 내가 쓴 공지사항 조회 (관리자용)
    @GetMapping("/me/notices")
    public ResponseEntity<List<NoticeResponseDto>> getMyNotices(Principal principal) {
        return ResponseEntity.ok(noticeService.getMyNoticeList(principal.getName()));
    }

    // 내가 등록한 상품 조회 (판매자용)
    @GetMapping("/me/products")
    public ResponseEntity<List<ProductResponseDto>> getMyProducts(Principal principal) {
        return ResponseEntity.ok(productService.getMyProducts(principal.getName()));
    }
}