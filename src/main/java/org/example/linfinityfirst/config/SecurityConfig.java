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
                        .requestMatchers(HttpMethod.POST,"/api/qna").hasAnyRole("USER") // 질문 등록
                        .requestMatchers(HttpMethod.PUT,"/api/qna/{qnaId}").hasAnyRole("USER", "ADMIN", "ROOT", "SELLER")   // 질문 수정 : 본인, 관리자
                        .requestMatchers(HttpMethod.DELETE, "/api/qna/{qnaId}").hasAnyRole("USER", "ADMIN", "ROOT", "SELLER") // 질문 삭제 : 본인, 관리자
                        .requestMatchers(HttpMethod.POST, "/api/qna/{qnaId}/answer").hasAnyRole("ADMIN", "ROOT")    // 질문 답변 : 관리자

                        // PRODUCT 역할 기반 API (SELLER, ADMIN, ROOT 필요)
                        // 상품 등록: SELLER만 가능
                        .requestMatchers(HttpMethod.POST,"/api/product").hasAnyRole("SELLER")
                        // 상품 수정/삭제: SELLER, ADMIN, ROOT 가능
                        .requestMatchers(HttpMethod.PUT,"/api/product/{productId}").hasAnyRole("SELLER", "ADMIN", "ROOT")
                        .requestMatchers(HttpMethod.DELETE, "/api/product/{productId}").hasAnyRole("SELLER", "ADMIN", "ROOT")

                        .requestMatchers("/qna/write").hasAnyRole("USER", "SELLER") // QnA 글쓰기 페이지 : 사용자, 판매자

                        // PRODUCT 조회 API (GET /api/product)
                        // Access Denied 오류 방지를 위해, 가장 먼저 걸리는 곳에 추가해야 합니다.
                        .requestMatchers(HttpMethod.GET, "/api/product").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product/{productId}").permitAll()

                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/signup").permitAll()    // 회원가입 요청 : 전부
                        .requestMatchers(
                                "/signup",          // 회원가입 페이지
                                "/",                // 메인페이지
                                "/login",           // 로그인 페이지
                                "/qna",             // QnA 게시판
                                "/qna/{id}",        // QnA 게시글 상세 페이지
                                "/api/qna",         // QnA 목록 조회
                                "/api/qna/{qnaId}",  // QnA 상세 내용 조회

                                // PRODUCT 페이지 경로 (permitAll)
                                "/products",             // 상품 목록 페이지
                                "/products/{id}",        // 상품 상세 페이지
                                "/product/register",     // 상품 등록 페이지 (화면 접근)
                                "/products/edit/{id}"   // 상품 수정 페이지 (화면 접근)
                        ).permitAll()   // 권한 필요 없음
                        .anyRequest().authenticated() //
                );

        http
                .formLogin(form -> form
                        .loginPage("/login")    // 컨트롤러의 url
                        .loginProcessingUrl("/login")   // 로그인 폼 action에 이 url이 들어가야함
                        .usernameParameter("username")  // html에 있는 username의 name과 맞아야함
                        .passwordParameter("password")  // html에 있는 password의 name과 맞아야함
                        .defaultSuccessUrl("/", true)  // 로그인 페이지로 직접 접속해서 로그인 성공 시 PageController의 @GetMapping("/")과 연결
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