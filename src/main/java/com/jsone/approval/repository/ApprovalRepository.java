package com.jsone.approval.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jsone.approval.dto.ChatDTO;
import com.jsone.approval.dto.ListDTO;
import com.jsone.approval.dto.LoginDTO;
import com.jsone.approval.dto.ViewDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApprovalRepository {
	
	private final SqlSessionTemplate sql;

    public LoginDTO loginProcess(LoginDTO loginDTO) {
        sql.selectOne("Approval.login", loginDTO);
        return loginDTO;
    }

    public List<ListDTO> list (Map<String, String> param) {
        return sql.selectList("Approval.list", param);
    }

    public ViewDTO view (Long id) {
        return sql.selectOne("Approval.view", id);
    }

    public List<ChatDTO> chat (Long id) {
        return sql.selectList("Approval.chat", id);
    }
}
