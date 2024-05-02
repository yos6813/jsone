package com.jsone.approval.dto;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ChatDTO {
    private Long docid;
    private Long empid;
    private String emp_nm;
    private String contents;
    private String create_date;
}
