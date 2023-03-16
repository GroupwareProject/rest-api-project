package com.project.groupware.member.service;

import com.project.groupware.exception.LoginFailedException;
import com.project.groupware.jwt.TokenProvider;
import com.project.groupware.member.dto.MemberDTO;
import com.project.groupware.member.dto.TokenDTO;
import com.project.groupware.member.entity.Member;
import com.project.groupware.member.entity.MemberRole;
import com.project.groupware.member.repository.MemberRepository;
import com.project.groupware.member.repository.MemberRoleRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final ModelMapper modelMapper;
    private final MemberRoleRepository memberRoleRepository;

    @Autowired
    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder
            , TokenProvider tokenProvider, ModelMapper modelMapper
            , MemberRoleRepository memberRoleRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
        this.memberRoleRepository = memberRoleRepository;
    }

    public Object login(MemberDTO memberDTO) {
        log.info("[AuthService] Login Start =====================");
        log.info("[AuthService] {}", memberDTO);

        Member member = memberRepository.findByMemberCode(memberDTO.getMemberCode());
        log.info("[AuthService =======] {}", member);
        if(member == null) {
            throw new LoginFailedException(memberDTO.getMemberCode() + "를 찾을 수 없습니다.");
        }


        if(!passwordEncoder.matches(memberDTO.getMemberPwd(), member.getMemberPwd())) {
            log.info("[AuthService] Password Match Fail!!!");
            throw new LoginFailedException("잘못된 비밀번호 입니다.");
        }

        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(member);
        log.info("[AuthService] tokenDTO {}", tokenDTO);

        log.info("[AuthService] Login End ======================================");
        return tokenDTO;
    }


    @Transactional
    public MemberDTO registMember(MemberDTO memberDTO) {

        log.info("[AuthService] Signup Start =================");
        log.info("[AuthService] memberDTO {}", memberDTO);

        Member registMember = modelMapper.map(memberDTO, Member.class);

        registMember.setMemberPwd(passwordEncoder.encode(registMember.getMemberPwd()));
        Member result1 = memberRepository.save(registMember);

        int maxMemberCode = memberRepository.maxMemberCode();

        MemberRole registMemberRole = new MemberRole(maxMemberCode, 2);

        MemberRole result2 = memberRoleRepository.save(registMemberRole);

        log.info("[AuthService] Member Insert Result {}",
                (result1 != null && result2 != null) ? "신규 직원 등록 성공" : "신규 직원 등록 실패");

        log.info("[AuthService] Signup End ==================================");

        return memberDTO;
    }
}
