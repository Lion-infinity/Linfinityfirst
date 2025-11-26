package org.example.linfinityfirst.security;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.domain.User;
import org.example.linfinityfirst.repository.UserRepository;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;//주석처리된부분

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //UserRepository userRepository;
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " 사용자를 찾을 수 없습니다."));

        //User user = userRepository.findByUsername(username);    // jpaRepository의 사용자 검색 메소드

        // 사용자 검색 실패 시 예외처리
        if (user == null){
            throw new UsernameNotFoundException(username + "User not found");
        }

        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);   // User 클래스를 제작해서 사용하고 때문에 spring security의 user를 import하지 못함

        userBuilder.password(user.getPassword());
        userBuilder.roles(
                user.getRole()              // user의 Role 객체 받아옴
                        .getRoleName()      // roleName 가져옴
        );

        return userBuilder.build();
    }
}
