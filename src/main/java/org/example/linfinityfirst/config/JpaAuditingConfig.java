package org.example.linfinityfirst.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //JPA Auditing 기능(작성일/수정일 자동 주입)을 활성화.
public class JpaAuditingConfig {
}