package com.project.groupware.exception;

import com.project.groupware.exception.dto.ApiExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/* 예외 처리용 어드바이스 추가 */
@RestControllerAdvice
public class ApiExceptionAdvice {
	
	/* AuthService에서 비밀번호 불일치 시 발생하는 예외 상황 처리 */
	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<ApiExceptionDTO> exceptionHandler(LoginFailedException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
	}
	
	/* 
	 * TokenProvider에서 토큰 유효성 검사용 메소드 정의 시 사용되며 유효성 검사 메소드는 JwtFilter에서 토큰 유효성
	 * 검사 시 발생하는 예외 상황 처리 
	 */
	@ExceptionHandler(TokenException.class)
	public ResponseEntity<ApiExceptionDTO> exceptionHandler(TokenException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiExceptionDTO(HttpStatus.UNAUTHORIZED, e.getMessage()));
				
	}
	
	/* 이메일이 중복됐을 때 발생하는 예외 상황 처리 */
	@ExceptionHandler(DuplicatedMemberEmailException.class)
	public ResponseEntity<ApiExceptionDTO> exceptionHandler(DuplicatedMemberEmailException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
	} 
}




