package com.project.groupware.member.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_auth", catalog = "finaldb")
public class Authority {

    @Id
    @Column(name = "AUTH_CODE")
    private int authorityCode;

    @Column(name = "AUTH_NAME")
    private String authorityName;

}
