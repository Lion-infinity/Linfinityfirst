package org.example.linfinityfirst.config;

import lombok.RequiredArgsConstructor;
import org.example.linfinityfirst.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        // 1. 정적 자원 및 에러 페이지 허용 (CSS, JS, 이미지 등)
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico", "/error").permitAll()

                        // ------------------------------------------------------------
                        // [View] 화면 페이지 권한 설정
                        // ------------------------------------------------------------

                        // (1) 공통 페이지 (메인, 로그인, 가입) - 누구나
                        .requestMatchers("/", "/login", "/signup").permitAll()

                        // (2) 공지사항 (목록/상세: 누구나, 글쓰기: 관리자)
                        .requestMatchers("/notices", "/notices/{id}").permitAll()
                        .requestMatchers("/notices/write").hasAnyRole("ADMIN", "ROOT")

                        // (3) QnA (목록/상세: 누구나, 글쓰기: 회원/판매자/관리자)
                        .requestMatchers("/qna", "/qna/{id}").permitAll()
                        .requestMatchers("/qna/write").hasAnyRole("USER", "SELLER", "ADMIN", "ROOT")

                        // (4) 상품 (목록/상세: 누구나, 등록/수정: 판매자/관리자)
                        .requestMatchers("/products", "/products/{id}").permitAll()
                        .requestMatchers("/product/register", "/products/edit/{id}").hasAnyRole("SELLER", "ADMIN", "ROOT")


                        // ------------------------------------------------------------
                        // [API] 데이터 요청 권한 설정 (/api/...)
                        // ------------------------------------------------------------

                        // (1) 회원가입 API
                        .requestMatchers(HttpMethod.POST,"/api/auth/signup").permitAll()

                        // (2) 조회(GET) API는 모두 허용 (공지, QnA, 상품)
                        .requestMatchers(HttpMethod.GET, "/api/notices/**", "/api/qna/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product/**", "/api/products/**").permitAll() // Product Controller 경로에 맞게 허용

                        // (3) 공지사항 CUD (관리자만)
                        .requestMatchers("/api/notices/**").hasAnyRole("ADMIN", "ROOT")

                        // (4) QnA 로직
                        // 답변 등록: 관리자, 판매자
                        .requestMatchers(HttpMethod.POST, "/api/qna/*/answer").hasAnyRole("ADMIN", "ROOT", "SELLER")
                        // 질문 작성/수정/삭제: 로그인한 사용자 (본인 확인은 Service에서 수행)
                        .requestMatchers(HttpMethod.POST, "/api/qna").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/qna/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/qna/**").authenticated()

                        // (5) 상품(Product) 로직 (판매자, 관리자)
                        // 상품 등록
                        .requestMatchers(HttpMethod.POST, "/api/product", "/api/products").hasAnyRole("SELLER", "ADMIN", "ROOT")
                        // 상품 수정/삭제
                        .requestMatchers(HttpMethod.PUT, "/api/product/**", "/api/products/**").hasAnyRole("SELLER", "ADMIN", "ROOT")
                        .requestMatchers(HttpMethod.DELETE, "/api/product/**", "/api/products/**").hasAnyRole("SELLER", "ADMIN", "ROOT")

                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                );

        http
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", false) // true로 하면 강제로 메인으로 이동하지만, false면 원래 가려던 페이지로 보내줌
                        .permitAll()
                );

        http
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );

        http
                .userDetailsService(customUserDetailsService);

        http
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .sessionRegistry(sessionRegistry)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}