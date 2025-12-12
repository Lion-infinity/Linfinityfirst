package org.example.linfinityfirst.service;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.domain.Role;
import org.example.linfinityfirst.domain.User;
import org.example.linfinityfirst.dto.request.LoginRequestDto;
import org.example.linfinityfirst.dto.request.SignupRequestDto;
import org.example.linfinityfirst.dto.response.UserSummaryResponse; // DTO import 필요
import org.example.linfinityfirst.repository.RoleRepository;
import org.example.linfinityfirst.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(SignupRequestDto requestDto) {
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        // 패스워드 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 권한 조회
        Role role = roleRepository.findByRoleName(requestDto.getRoleName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 권한입니다."));

        User user = new User(requestDto.getUsername(), encodedPassword, requestDto.getEmail(), role);
        return userRepository.save(user).getId();
    }

    //마이페이지용 내 정보 조회
    @Transactional(readOnly = true)
    public UserSummaryResponse getMyInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserSummaryResponse.from(user);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}