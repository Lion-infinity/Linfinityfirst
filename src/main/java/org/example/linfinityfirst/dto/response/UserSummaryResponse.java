package org.example.linfinityfirst.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.linfinityfirst.domain.User;

@Getter
@AllArgsConstructor
public class UserSummaryResponse {
    private Long id;
    private String username;
    private String email;
    private String role; // ROLE_USER, ROLE_ADMIN 등

    // Entity -> DTO 변환 메서드
    public static UserSummaryResponse from(User user) {
        return new UserSummaryResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getRoleName() // Role 객체에서 역할 이름 추출
        );
    }
}