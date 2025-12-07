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

    // 공지사항 등록 (관리자만)
    @Transactional
    public Long createNotice(NoticeRequestDto dto, String username) {
        User user = getUserOrThrow(username);
        checkAdminRole(user); // 권한 체크

        Notice notice = new Notice(dto.getTitle(), dto.getContent(), user);
        return noticeRepository.save(notice).getId();
    }

    // 공지사항 수정 (관리자만)
    @Transactional
    public void updateNotice(Long id, NoticeRequestDto dto, String username) {
        Notice notice = getNoticeOrThrow(id);
        User user = getUserOrThrow(username);
        checkAdminRole(user); // 권한 체크

        notice.update(dto.getTitle(), dto.getContent());
    }

    // 공지사항 삭제 (관리자만)
    @Transactional
    public void deleteNotice(Long id, String username) {
        Notice notice = getNoticeOrThrow(id);
        User user = getUserOrThrow(username);
        checkAdminRole(user); // 권한 체크

        noticeRepository.delete(notice);
    }

    // 공지사항 상세 조회 (누구나, 조회수 증가)
    @Transactional
    public NoticeResponseDto getNotice(Long id) {
        Notice notice = getNoticeOrThrow(id);
        notice.increaseViewCount(); // 조회수 증가 (Dirty Checking)
        return NoticeResponseDto.from(notice);
    }

    // 공지사항 전체 목록 (누구나, 최신순)
    @Transactional(readOnly = true)
    public List<NoticeResponseDto> getAllNotices() {
        // [수정] Repository에 정의한 최신순 정렬 메서드 사용
        return noticeRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(NoticeResponseDto::from)
                .collect(Collectors.toList());
    }

    // --- 헬퍼 메서드 ---
    private User getUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private Notice getNoticeOrThrow(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    private void checkAdminRole(User user) {
        String role = user.getRole().getRoleName();
        // DB에 접두사가 있든 없든 모두 체크
        boolean isAdmin = role.equals("ADMIN") || role.equals("ROOT") ||
                role.equals("ROLE_ADMIN") || role.equals("ROLE_ROOT");

        if (!isAdmin) {
            throw new IllegalArgumentException("관리자 권한이 필요합니다.");
        }
    }
}