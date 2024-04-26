package com.jsone.approval.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jsone.approval.dto.ListDTO;
import com.jsone.approval.dto.LoginDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApprovalRepository {
	
	private final SqlSessionTemplate sql;

    public LoginDTO loginProcess(LoginDTO loginDTO) {
        sql.selectOne("Approval.login", loginDTO);
        return loginDTO;
    }

    public List<ListDTO> list () {
        return sql.selectList("Approval.list");
    }

}
