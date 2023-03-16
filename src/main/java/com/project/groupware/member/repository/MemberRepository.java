package com.project.groupware.member.repository;

import com.project.groupware.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByMemberCode(long memberCode);

    Member findByMemberName(String memberName);

    @Query("SELECT MAX(m.memberCode) FROM Member m")
    int maxMemberCode();
}
