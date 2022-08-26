package com.sparta.matchgi.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static com.sparta.matchgi.config.security.JpaDataConfig.RDS_DOMAIN_PACKAGE;

@RequiredArgsConstructor
@Configuration
@EnableJpaRepositories(basePackages = {RDS_DOMAIN_PACKAGE}) // JpaRepository 패키지 위치 등록
@EnableConfigurationProperties({JpaProperties.class, HibernateProperties.class})
public class JpaDataConfig {

    static final String RDS_DOMAIN_PACKAGE = "com.sparta.matchgi.repository";
}
