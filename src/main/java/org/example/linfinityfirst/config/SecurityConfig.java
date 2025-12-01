package org.example.linfinityfirst.config;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SessionRegistry sessionRegistry) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/", "login", "/qna", "/qna/{id}").permitAll()   // 회원가입, 인덱스 페이지, 로그인 페이지, QnA 게시판, QnA 게시글 상세 화면은 누구나 접근 가능
                        .requestMatchers("/qna/write").hasAnyRole("USER", "ADMIN", "ROOT", "SELLER") // QnA 글쓰기 게시판은 비회원 빼고 모두 접근
                        .anyRequest().authenticated()
                );

        http
                .formLogin(form -> form
                        .loginPage("/login")    // 컨트롤러의 url
                        .loginProcessingUrl("/login")   // 로그인 폼 action에 이 url이 들어가야함
                        .usernameParameter("username")  // html에 있는 username의 name과 맞아야함
                        .passwordParameter("password")  // html에 있는 password의 name과 맞아야함
                        .defaultSuccessUrl("/", false)  // 로그인 페이지로 직접 접속해서 로그인 성공 시 PageController의 @GetMapping("/")과 연결
                        .permitAll()
                );

        http
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")  // 로그아웃 시 index.html로 연결
                );

        http
                .userDetailsService(customUserDetailsService);  // userDetailsService로 customUserDetailsService를 쓰겠다.

        http
                .sessionManagement(session -> session
                        .maximumSessions(1)                 // 동시접속 허용 수 설정
                        .maxSessionsPreventsLogin(false)    // 기본값 false -> 먼저 로그인한 사용자가 차단됨.
                        .sessionRegistry(sessionRegistry)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}