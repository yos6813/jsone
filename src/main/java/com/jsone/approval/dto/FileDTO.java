package com.jsone.approval.dto;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class FileDTO {
    private Long attachid;
    private Long docid;
    private String file_path;
    private String original_file_name;
}
