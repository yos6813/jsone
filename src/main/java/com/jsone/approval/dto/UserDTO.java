package com.jsone.approval.dto;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class UserDTO {
    private String empid;
    private String emp_nm;
    private String coop_cd;
    private String pos_cd;
}
