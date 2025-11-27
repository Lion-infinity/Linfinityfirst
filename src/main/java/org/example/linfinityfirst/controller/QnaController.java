package org.example.linfinityfirst.controller;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.dto.request.QnaAnswerRequestDto; // import 확인!
import org.example.linfinityfirst.dto.request.QnaRequestDto;
import org.example.linfinityfirst.dto.response.QnaResponseDto;
import org.example.linfinityfirst.service.QnaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    // 1. 질문 등록 (USER)
    @PostMapping
    public ResponseEntity<Long> createQna(@RequestBody QnaRequestDto requestDto, Principal principal) {
        Long qnaId = qnaService.createQna(requestDto, principal.getName());
        return ResponseEntity.ok(qnaId);
    }

    // 2. 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<QnaResponseDto>> getAllQna() {
        return ResponseEntity.ok(qnaService.getAllQna());
    }

    // 3. 답변 등록 (ROOT,ADMIN)
    @PostMapping("/{qnaId}/answer")
    public ResponseEntity<String> answerQna(@PathVariable Long qnaId,
                                            @RequestBody QnaAnswerRequestDto answerDto) { // [변경]

        qnaService.answerQna(qnaId, answerDto.getAnswer());

        return ResponseEntity.ok("답변이 등록되었습니다.");
    }
}