package org.example.linfinityfirst.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 역할(Role) 엔티티
 */

@Entity
@Getter
@NoArgsConstructor
@Table(name = "roles")
public class Role {

    //역할 고유 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //역할 이름 (관리자, 판매자, 고객)
    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
