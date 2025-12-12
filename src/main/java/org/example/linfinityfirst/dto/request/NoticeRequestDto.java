package org.example.linfinityfirst.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 공지사항 글 등록시 DTO
 */

@Getter
@NoArgsConstructor
public class NoticeRequestDto {
    private String title;
    private String content;
}