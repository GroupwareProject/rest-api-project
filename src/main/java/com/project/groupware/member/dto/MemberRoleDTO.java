package com.project.groupware.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRoleDTO {

    private long memberNo;

    private int authorityNo;

    private AuthorityDTO authority;
}
