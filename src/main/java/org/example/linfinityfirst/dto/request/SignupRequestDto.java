package org.example.linfinityfirst.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 회원가입 요청 DTO
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    private String username;
    private String password;
    private String email;

    //예시 "ROLE_USER", "ROLE_SELLER", "ROLE_ADMIN"
    private String roleName;

}