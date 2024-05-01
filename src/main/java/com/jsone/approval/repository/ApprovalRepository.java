package com.jsone.approval.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jsone.approval.dto.ChatDTO;
import com.jsone.approval.dto.CustDTO;
import com.jsone.approval.dto.ListDTO;
import com.jsone.approval.dto.LoginDTO;
import com.jsone.approval.dto.UserDTO;
import com.jsone.approval.dto.ViewDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApprovalRepository {
	
	private final SqlSessionTemplate sql;

    public LoginDTO loginProcess(Map<String, String> param) {
        return sql.selectOne("Approval.login", param);
    }

    public void use(String dbname) {
        sql.selectOne("Approval.use", dbname);
    }

    public CustDTO customer(Long custid) {
        return sql.selectOne("Approval.customer", custid);
    }

    public UserDTO user(Map<String,String> param) {
        return sql.selectOne("Approval.user", param);
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
