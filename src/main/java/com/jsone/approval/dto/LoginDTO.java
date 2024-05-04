package com.jsone.approval.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDTO {
    private String empid;
    private Long custid;
    private String loginid;
    private String passwd;
    private char authyn;
}
