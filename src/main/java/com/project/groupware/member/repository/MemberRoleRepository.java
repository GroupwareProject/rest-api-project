package com.project.groupware.member.repository;

import com.project.groupware.member.entity.MemberRole;
import com.project.groupware.member.entity.MemberRolePk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, MemberRolePk> {
}
