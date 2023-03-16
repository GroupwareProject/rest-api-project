package com.project.groupware.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.project.groupware")
@EnableJpaRepositories(basePackages = "com.project.groupware")
public class JpaConfiguration {
}
