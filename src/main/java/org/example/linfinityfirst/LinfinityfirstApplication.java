package org.example.linfinityfirst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LinfinityfirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinfinityfirstApplication.class, args);
    }

}
