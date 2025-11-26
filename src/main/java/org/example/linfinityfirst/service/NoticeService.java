package org.example.linfinityfirst.service;
/*
import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.domain.Notice;
import org.example.linfinityfirst.domain.User;
import org.example.linfinityfirst.dto.request.NoticeRequestDto; // dto 필요(title, content)
import org.example.linfinityfirst.dto.response.NoticeResponseDto; // dto 필요
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

    @Transactional
    public Long createNotice(NoticeRequestDto dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Notice notice = new Notice(dto.getTitle(), dto.getContent(), user);
        return noticeRepository.save(notice).getId();
    }

    @Transactional(readOnly = true)
    public List<NoticeResponseDto> getAllNotices() {
        return noticeRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(NoticeResponseDto::from)
                .collect(Collectors.toList());
    }
}

 */