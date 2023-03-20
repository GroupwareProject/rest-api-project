package com.project.groupware.member.controller;

import com.project.groupware.common.ResponseDTO;
import com.project.groupware.member.dto.MemberDTO;
import com.project.groupware.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//   회원 조회(개인정보)
    @Operation(summary = "회원 한 명 조회 요청", description = "회원 한 명이 조회됩니다.", tags = {"MemberController"})
    @GetMapping("/members/{memberCode}")
    public ResponseEntity<ResponseDTO> selectMyMemberInfo(@PathVariable int memberCode){

        System.out.println("memberCode ===================== " + memberCode);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원 조회 성공", memberService.selectMyInfo(memberCode)));
    }

//   회원 조회(관리자모드)
    @Operation(summary = "회원 리스트 조회 요청", description = "회원 리스트가 조회됩니다.", tags = {"MemberController"})
    @GetMapping("/members")
    public ResponseEntity<ResponseDTO> selectMemberList(){

        System.out.println("회원 전체 조회 컨트롤러입니다.");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원 리스트 조회 성공", memberService.selectMemberList()));
    }

//  회원 수정(관리자모드)
    @Operation(summary = "회원 수정 요청", description = "회원 정보가 수정됩니다.", tags = {"MemberController"})
    @PutMapping("/members/update/{memberCode}")
    public ResponseEntity<ResponseDTO> updateMember(@RequestBody MemberDTO memberDTO, @PathVariable int memberCode){

        System.out.println("회원 수정 컨트롤러입니다.");
        System.out.println("memberDTO ============= " + memberDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원 수정 성공", memberService.updateMember(memberDTO, memberCode)));
    }

    //  회원 삭제(관리자모드)

    @Operation(summary = "회원 삭제 요청", description = "회원 정보가 삭제됩니다.", tags = {"MemberController"})
    @DeleteMapping ("/members/delete/{memberCode}")
    public ResponseEntity<ResponseDTO> deleteMember(@PathVariable int memberCode){

        System.out.println("회원 삭제 컨트롤러입니다.");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원 삭제 성공", memberService.deleteMember(memberCode)));
    }
}
