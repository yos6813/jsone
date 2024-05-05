package com.jsone.approval.dto;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ListDTO {
    private int docid;
    private int empid;
    private String title;
    private String contents;
    private String contents_text;
    private String create_date;
    private String startDate;
    private String endDate;
    private String search;
    private String status_cd;
    private String type_cd;
    private String emp_nm;
}
