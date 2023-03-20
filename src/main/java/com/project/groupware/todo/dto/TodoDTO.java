package com.project.groupware.todo.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    private long todoNo;

    private long memberCode;

    private String todoTitle;

    private String todoContent;

    private Date startDate;
    private Date endDate;
}
