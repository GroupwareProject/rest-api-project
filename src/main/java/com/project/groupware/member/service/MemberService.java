package com.project.groupware.member.service;

import com.project.groupware.member.dto.MemberDTO;
import com.project.groupware.member.entity.Member;
import com.project.groupware.member.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    //   회원 조회(개인정보)
    public MemberDTO selectMyInfo(int memberCode) {

        log.info("[MemberService] getMyInfo Start ======");

        Member member = memberRepository.findByMemberCode(memberCode);
        log.info("[MemberService] {}", member);
        log.info("[MemberService] getMyInfo End==========");

        return modelMapper.map(member, MemberDTO.class);
    }

    //   회원 조회(관리자모드)
    public List<MemberDTO> selectMemberList() {
        log.info("[MemberService] selectMemberList Start ======");

        List<Member> memberList = memberRepository.findAll();

        log.info("[MemberService] memberList {}", memberList);

        log.info("[MemberService] selectMemberList End ======");

        return memberList.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());
    }

    //  회원 수정(관리자모드)
    @Transactional
    public Object updateMember(MemberDTO memberDTO, int memberCode) {
        log.info("[MemberService] updateMember Start ======");
        System.out.println("memberDTO =============================== " + memberDTO);

        int result = 0;

        try {
            Member member = memberRepository.findByMemberCode(memberCode);

            member.setMemberCode(memberDTO.getMemberCode());
            member.setDeptCode(memberDTO.getDeptCode());
            member.setJobCode(memberDTO.getJobCode());
            member.setMemberPwd(passwordEncoder.encode(memberDTO.getMemberPwd()));
            member.setMemberName(memberDTO.getMemberName());
            member.setMemberBirth(memberDTO.getMemberBirth());
            member.setMemberPhone(memberDTO.getMemberPhone());
            member.setMemberEmail(memberDTO.getMemberEmail());
            member.setMemberAddress(memberDTO.getMemberAddress());
            member.setMemberExtension(memberDTO.getMemberExtension());
            member.setMemberStartDate(memberDTO.getMemberStartDate());
            member.setMemberEndDate(memberDTO.getMemberEndDate());
            member.setMemberIsOut(memberDTO.getMemberIsOut());

            memberRepository.save(member);

            result = 1;
        } catch (Exception e) {
            log.info("[member update] Exception!!!!!!!");
        }
            log.info("[MemberService] updateMember End ======");

            return (result > 0) ? "회원 수정 성공 :)" : "회원 수정 실패 :(";
    }

    //  회원 삭제(관리자모드)
    @Transactional
    public String deleteMember(int memberCode) {

        log.info("[MemberService] deleteMember Start ======");

        System.out.println("memberCode = " + memberCode);

        Optional<Member> member = memberRepository.findById(memberCode);

        System.out.println("member ========== " + member);
        int result = 0;


        try {
//            memberRepository.deleteByMemberCode(memberCode);
            //memberRepository.delete(member.get());
            memberRepository.deleteById(member.get().getMemberCode());
            result = 1;
        } catch (Exception e){
            log.error("[MemberService] deleteMember 에러", e);
        }
        return (result > 0) ? "회원 삭제 성공 :)" : "회원 삭제 실패 :(";
    }
}


