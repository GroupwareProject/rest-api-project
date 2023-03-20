package com.project.groupware.member.dto;

import com.project.groupware.member.entity.Member;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO implements UserDetails {

    private int memberCode;
    private String memberPwd;
    private String memberName;
    private Date memberBirth;
    private String memberPhone;
    private String memberEmail;
    private String memberAddress;
    private String deptCode;
    private String jobCode;
    private String memberExtension;
    private Date memberStartDate;
    private Date memberEndDate;
    private int memberIsOut;

    private List<MemberRoleDTO> memberRole;

    private Collection<GrantedAuthority> authorities;

    /* setter 추가할 것 */
    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    @Override
    public String getPassword() {
        return this.memberPwd;
    }
    @Override
    public String getUsername() {
        return this.memberName;
    }
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }
    public void delete(Member member) {
    }
}

