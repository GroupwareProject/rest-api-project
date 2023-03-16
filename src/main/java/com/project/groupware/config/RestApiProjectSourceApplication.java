package com.project.groupware.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.project.groupware")
public class RestApiProjectSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiProjectSourceApplication.class, args);


	}

}
