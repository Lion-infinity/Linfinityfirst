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
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).
                orElseThrow(() -> {
                    return new UsernameNotFoundException(username);
                });

        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);   // User 클래스를 제작해서 사용하고 때문에 spring security의 user를 import하지 못함

        userBuilder.password(user.getPassword());
        userBuilder.roles(
                user.getRole()              // user의 Role 객체 받아옴
                        .getRoleName()      // roleName 가져옴
        );

        return userBuilder.build();
    }
}