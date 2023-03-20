package com.project.groupware.todo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "tbl_todo", catalog = "finaldb")
public class Todo {

    @Id
    @Column(name = "TODO_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long todoNo;

    @Column(name = "MEMBER_CODE")
    private long memberCode;

    @Column(name= "TODO_TITLE")
    private String todoTitle;

    @Column(name = "TODO_CONTENT")
    private String todoContent;

    @Column(name = "TODO_STARTDATE")
    private Date startDate;

    @Column(name = "TODO_ENDDATE")
    private Date endDate;
}
