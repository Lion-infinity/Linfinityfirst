package org.example.linfinityfirst.service;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.domain.Qna;
//import org.example.linfinityfirst.domain.Role;
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

    // 유저 질문 작성
    @Transactional
    public Long createQna(QnaRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Qna qna = new Qna(user, requestDto.getTitle(), requestDto.getQuestion());
        return qnaRepository.save(qna).getId();
    }

    // 질문 수정 (유저/어드민)
    @Transactional
    public void updateQna(Long qnaId, QnaRequestDto requestDto, String username) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        // 유저/어드민 아니면 예외 발생
        if (!qna.getUser().getUsername().equals(username) && !isAdmin(currentUser)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        qna.updateQuestion(requestDto.getTitle(), requestDto.getQuestion());
    }

    // 질문 삭제 유저/어드민
    @Transactional
    public void deleteQna(Long qnaId, String username) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        // 유저/어드민 아니면 예외 발생
        if (!qna.getUser().getUsername().equals(username) && !isAdmin(currentUser)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        qnaRepository.delete(qna);
    }

    // 어드민 확인
    private boolean isAdmin(User user) {
        return "ROLE_ADMIN".equals(user.getRole().getRoleName()) || "ADMIN".equals(user.getRole().getRoleName());
    }

    // 답변 등록 관리자
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

    // 상세 조회
    @Transactional(readOnly = true)
    public QnaResponseDto getQna(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));
        return QnaResponseDto.from(qna);
    }

    //내가 쓴 QnA 목록 조회 (마이페이지용)
    @Transactional(readOnly = true)
    public List<QnaResponseDto> getMyQnaList(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // QnaRepository에 findAllByUserOrderByCreatedAtDesc 메서드 필요
        return qnaRepository.findAllByUserOrderByCreatedAtDesc(user).stream()
                .map(QnaResponseDto::from)
                .collect(Collectors.toList());
    }
}