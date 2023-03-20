package com.project.groupware.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_member", catalog = "finaldb")
public class Member {

    @Id
    @Column(name = "MEMBER_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberCode;

    @Column(name = "MEMBER_PWD")
    private String memberPwd;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "MEMBER_BIRTH")
    private Date memberBirth;

    @Column(name = "MEMBER_PHONE")
    private String memberPhone;

    @Column(name = "MEMBER_EMAIL")
    private String memberEmail;

    @Column(name = "MEMBER_ADDRESS")
    private String memberAddress;

    @Column(name = "DEPT_CODE")
    private String deptCode;

    @Column(name = "JOB_CODE")
    private String jobCode;

    @Column(name = "MEMBER_EXTENSION")
    private String memberExtension;

    @Column(name = "MEMBER_STARTDATE")
    private Date memberStartDate;

    @Column(name = "MEMBER_ENDDATE")
    private Date memberEndDate;

    @Column(name = "MEMBER_ISOUT")
    private int memberIsOut;

    @OneToMany
    @JoinColumn(name = "MEMBER_CODE")
    private List<MemberRole> memberRole;

    @Override
    public String toString() {
        return "Member{" +
                "memberCode=" + memberCode +
                ", memberPwd='" + memberPwd + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberBirth=" + memberBirth +
                ", memberPhone='" + memberPhone + '\'' +
                ", memberEmail='" + memberEmail + '\'' +
                ", memberAddress='" + memberAddress + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", jobCode='" + jobCode + '\'' +
                ", memberExtension=" + memberExtension +
                ", memberStartDate=" + memberStartDate +
                ", memberEndDate=" + memberEndDate +
                ", memberIsOut=" + memberIsOut +
                ", memberRole=" + memberRole +
                '}';
    }

}
