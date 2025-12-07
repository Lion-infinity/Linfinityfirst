package org.example.linfinityfirst.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.linfinityfirst.domain.Notice;
import java.time.LocalDateTime;

/*
 * 공지사항 조회시 사용 Dto
 */
@Getter
@AllArgsConstructor
public class NoticeResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writerName;
    private int viewCount;
    private LocalDateTime createdAt;

    public static NoticeResponseDto from(Notice notice) {
        return new NoticeResponseDto(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getUser().getUsername(),
                notice.getViewCount(),
                notice.getCreatedAt()
        );
    }
}