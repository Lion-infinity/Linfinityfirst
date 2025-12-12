package org.example.linfinityfirst.service;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.domain.Notice;
import org.example.linfinityfirst.domain.User;
import org.example.linfinityfirst.dto.request.NoticeRequestDto;
import org.example.linfinityfirst.dto.response.NoticeResponseDto;
import org.example.linfinityfirst.repository.NoticeRepository;
import org.example.linfinityfirst.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    // 공지사항 등록
    @Transactional
    public Long createNotice(NoticeRequestDto dto, String username) {
        User user = getUserOrThrow(username);


        Notice notice = new Notice(dto.getTitle(), dto.getContent(), user);
        return noticeRepository.save(notice).getId();
    }

    // 공지사항 수정
    @Transactional
    public void updateNotice(Long id, NoticeRequestDto dto, String username) {
        Notice notice = getNoticeOrThrow(id);


        notice.update(dto.getTitle(), dto.getContent());
    }

    // 공지사항 삭제
    @Transactional
    public void deleteNotice(Long id) { // 삭제는 작성자 정보도 필요 없음 (관리자면 삭제 가능)
        Notice notice = getNoticeOrThrow(id);
        noticeRepository.delete(notice);
    }

    // 상세 조회
    @Transactional
    public NoticeResponseDto getNotice(Long id) {
        Notice notice = getNoticeOrThrow(id);
        notice.increaseViewCount();
        return NoticeResponseDto.from(notice);
    }

    // 전체 목록 조회
    @Transactional(readOnly = true)
    public List<NoticeResponseDto> getAllNotices() {
        return noticeRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(NoticeResponseDto::from)
                .collect(Collectors.toList());
    }

    // [마이페이지용] 내가 등록한 공지사항 목록 조회
    @Transactional(readOnly = true)
    public List<NoticeResponseDto> getMyNoticeList(String username) {
        User user = getUserOrThrow(username);
        return noticeRepository.findAllByUserOrderByCreatedAtDesc(user).stream()
                .map(NoticeResponseDto::from)
                .collect(Collectors.toList());
    }

    private User getUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private Notice getNoticeOrThrow(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }
}