package org.example.linfinityfirst.service;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.domain.Qna;
import org.example.linfinityfirst.domain.User;
import org.example.linfinityfirst.dto.request.QnaRequestDto;
import org.example.linfinityfirst.dto.response.QnaResponseDto;
import org.example.linfinityfirst.repository.QnaRepository;
import org.example.linfinityfirst.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;

    //질문생성
    @Transactional
    public Long createQna(QnaRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Qna qna = new Qna(user, requestDto.getTitle(), requestDto.getQuestion());
        return qnaRepository.save(qna).getId();
    }

    // 답변 등록
    @Transactional
    public void answerQna(Long qnaId, String answer) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        qna.updateAnswer(answer);
    }

    // 전체 목록 조회
    @Transactional(readOnly = true)
    public List<QnaResponseDto> getAllQna() {
        return qnaRepository.findAll().stream()
                .map(QnaResponseDto::from)
                .collect(Collectors.toList());
    }
}