package com.jsone.approval.dto;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ViewDTO {
    private Long docid;
    private Long empid;
    private String emp_nm;
    private String title;
    private String contents;
    private String contents_text;
    private String create_date;
    private Long reference_docid;
}
