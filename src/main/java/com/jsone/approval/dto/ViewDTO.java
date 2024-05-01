package com.jsone.approval.dto;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ViewDTO {
    private Long docid;
    private Long reference_docid;
    private Long empid;
    private String emp_nm;
    private String type_cd;
    private String status_cd;
    private String title;
    private String contents;
    private String contents_text;
    private String create_date;
    private String reference_id;
    private String doc_num;
}
