package com.project.groupware.board.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDTO {

    private long boardNo;
    private long memberCode;
    private String boardTitle;
    private String boardContent;
    private int boardViews;
    private Date boardDate;
}
