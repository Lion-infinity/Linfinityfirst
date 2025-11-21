package org.example.linfinityfirst.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.linfinityfirst.domain.User;

/*
 * 고객 정보 조회시 사용 Dto
 */


@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String role; // ROLE_USER 등

    //엔티티 -> dto 변환메서드
    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getRoleName() // Role 객체에서 이름만 꺼내기
        );
    }
}