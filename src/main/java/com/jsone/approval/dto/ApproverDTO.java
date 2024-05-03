package com.jsone.approval.dto;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ApproverDTO {
    private Long id;
    private String name;
    private Long empid;
    private String emp_nm;
}
