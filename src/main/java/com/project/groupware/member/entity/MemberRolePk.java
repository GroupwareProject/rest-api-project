package com.project.groupware.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRolePk implements Serializable {

    private long memberNo;

    private int authorityCode;

    @Override
    public String toString() {
        return "MemberRolePk{" +
                "memberNo=" + memberNo +
                ", authorityCode=" + authorityCode +
                '}';
    }
}
