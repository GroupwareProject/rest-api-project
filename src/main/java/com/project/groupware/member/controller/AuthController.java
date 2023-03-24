package com.project.groupware.member.controller;

import com.project.groupware.common.ResponseDTO;
import com.project.groupware.member.dto.MemberDTO;
import com.project.groupware.member.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "로그인 요청", description = "로그인 및 인증이 진행됩니다.", tags = {"AuthController"})
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody MemberDTO memberDTO) {
        System.out.println("memberDTO ================================= " + memberDTO);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK, "로그인 성공", authService.login(memberDTO)));
    }

    @Operation(summary = "신규 직원 등록 요청",
            description = "신규 직원 가입이 진행됩니다.", tags = {"AuthController"})
    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody MemberDTO memberDTO) {
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.CREATED,
                        "신규 직원 가입성공", authService.registMember(memberDTO)));
    }
}
