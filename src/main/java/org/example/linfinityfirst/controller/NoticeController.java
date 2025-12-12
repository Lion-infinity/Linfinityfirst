package org.example.linfinityfirst.controller;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.dto.request.NoticeRequestDto;
import org.example.linfinityfirst.dto.response.NoticeResponseDto;
import org.example.linfinityfirst.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 목록 조회
    @GetMapping
    public ResponseEntity<List<NoticeResponseDto>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    // 공지사항 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponseDto> getNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.getNotice(id));
    }

    // 공지사항 등록
    @PostMapping
    public ResponseEntity<Long> createNotice(@RequestBody NoticeRequestDto dto, Principal principal) {
        return ResponseEntity.ok(noticeService.createNotice(dto, principal.getName()));
    }

    // 공지사항 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateNotice(@PathVariable Long id, @RequestBody NoticeRequestDto dto, Principal principal) {
        noticeService.updateNotice(id, dto, principal.getName());
        return ResponseEntity.ok("수정되었습니다.");
    }

    // 공지사항 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}