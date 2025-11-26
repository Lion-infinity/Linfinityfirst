package org.example.linfinityfirst.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 사용자(User) 엔티티 (관리자,판매자,고객)
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    //사용자 구분짓는 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //사용자 이름(로그인시 아이디)
    @Column(unique = true, nullable = false)
    private String username;

    //사용자 비밀번호
    @Column(nullable = false, length = 100)
    private String password;

    private String email;

    //가입 일시
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)//lazy
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }
}
