package com.project.groupware.member.repository;

import com.project.groupware.member.dto.MemberDTO;
import com.project.groupware.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.zip.Checksum;
@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByMemberCode(int memberCode);

//    Member findByMemberName(String memberName);

    @Query("SELECT MAX(m.memberCode) FROM Member m")
    int maxMemberCode();

//    void deleteByMemberCode(long memberCode);
}
