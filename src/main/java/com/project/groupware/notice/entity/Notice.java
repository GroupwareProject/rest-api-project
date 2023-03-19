package com.project.groupware.notice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.groupware.member.entity.Member;
import lombok.*;

import javax.persistence.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "tbl_notice", catalog = "finaldb")
public class Notice {

    @Id
    @Column(name = "NOTICE_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noticeNo;

    @Column(name = "MEMBER_CODE")
    private long memberCode;

    @Column(name = "NOTICE_TITLE")
    private String noticeTitle;

    @Column(name = "NOTICE_CONTENT")
    private String noticeContent;

    @Column(name = "NOTICE_VIEWS", columnDefinition = "integer default 0", nullable = false)
    private int noticeViews;

    @Column(name = "NOTICE_DATE")
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date noticeDate;
//    @ManyToOne
//    @JoinColumn(name = "MEMBER_CODE", insertable = false, updatable = false )
//    private Member member;


}
