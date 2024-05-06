package com.jsone.approval.dto;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class SubCntDTO {
    private Long all;
    private Long internal;
    private Long external;
    private Long apply;
    private Long report;
    private Long work;
    private Long annual;
    private Long spend;
    private Long draft;
    private Long ongoing;
    private Long refer;
    private Long storage;
    private Long complete;
}
