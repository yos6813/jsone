package com.jsone.approval.dto;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ChatAjaxDTO {
    private String docid;
    private String empid;
    private String chatid;
    private String chatContents;
    private String emp_nm;
}
