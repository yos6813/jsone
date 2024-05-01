package com.jsone.approval.dto;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class CustDTO {
    private Long custid;
    private String manager_nm;
    private String db_nm;
}
