package com.project.groupware.board.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "tbl_board", catalog = "finaldb")
public class Board {

    @Id
    @Column(name = "BOARD_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardNo;

    @Column(name = "MEMBER_CODE")
    private long memberCode;

    @Column(name = "BOARD_TITLE")
    private String boardTitle;

    @Column(name = "BOARD_CONTENT")
    private String boardContent;

    @Column(name = "BOARD_VIEWS")
    private int boardViews;

    @Column(name = "BOARD_DATE")
    private Date boardDate;
}
