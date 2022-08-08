package com.sparta.matchgi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MatchgiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchgiApplication.class, args);
    }

}
