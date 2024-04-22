package com.jsone.approval.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDTO {
    private String id;
    private String password;
    private String authno;

}
