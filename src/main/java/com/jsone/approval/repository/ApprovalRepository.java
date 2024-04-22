package com.jsone.approval.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.jsone.approval.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApprovalRepository {
	
	private final SqlSessionTemplate sql;

    public LoginDTO loginProcess(LoginDTO loginDTO) {

        return loginDTO;
    }

}
