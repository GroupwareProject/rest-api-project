package com.project.groupware.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
		info = @Info(title = "API 명세서",
					 description = "React부터 Spring Data Jpa까지 진행하는 서비스 API 명세서",
					 version = "v1"))
@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi chatOpenApi() {
		String [] paths = {"/api/v1/**", "/auth/**"};	// Swagger에서 처리 되었으면 하는 경로 설정
		
		return GroupedOpenApi.builder()
							 .group("API v1")
							 .pathsToMatch(paths)
							 .build();
	}
}










