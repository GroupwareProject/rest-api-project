package com.project.groupware.notice.dto;

import com.project.groupware.notice.entity.Notice;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {

    private long noticeNo;
    private long memberCode;
    private String noticeTitle;
    private String noticeContent;
    private int noticeViews;
    private Date noticeDate;




}