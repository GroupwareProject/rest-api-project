package com.project.groupware.member.entity;

import lombok.AllArgsConstructor;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_auth_list", catalog = "finaldb")
@IdClass(MemberRolePk.class)
public class MemberRole {
    @Id
    @Column(name = "AUTH_CODE")
    private int authorityCode;

    @Column(name ="MEMBER_CODE")
    private long memberNo;

    @ManyToOne
    @JoinColumn(name = "AUTH_CODE", insertable = false, updatable = false)
    private Authority authority;

    public MemberRole() {
    }

    public MemberRole(int memberNo, int authorityCode) {
        this.memberNo = memberNo;
        this.authorityCode = authorityCode;
    }

    public MemberRole(int memberNo, int authorityCode, Authority authority) {
        this.memberNo = memberNo;
        this.authorityCode = authorityCode;
        this.authority = authority;
    }


    @Override
    public String toString() {
        return "MemberRole [memberNo=" + memberNo + ", authorityCode=" + authorityCode + ", authority=" + authority
                + "]";
    }
}



