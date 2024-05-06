package com.jsone.approval.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jsone.approval.dto.ApproverDTO;
import com.jsone.approval.dto.ChatAjaxDTO;
import com.jsone.approval.dto.ChatDTO;
import com.jsone.approval.dto.CustDTO;
import com.jsone.approval.dto.ListDTO;
import com.jsone.approval.dto.LoginDTO;
import com.jsone.approval.dto.SubCntDTO;
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

    public LoginDTO authCheck(String loginid) {
        return sql.selectOne("Approval.authCheck", loginid);
    }

    public Long findUser(Map<String, Object> map) {
        return sql.selectOne("Approval.findUser", map);
    }

    public void resetPw (Map<String, Object> map) {
        sql.update("Approval.resetPw", map);
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

    public List<UserDTO> userAll() {
        return sql.selectList("Approval.allUser");
    }

    public List<ListDTO> list (Map<String, String> param) {
        return sql.selectList("Approval.list", param);
    }

    public Long cnt (Map<String, String> param) {
        return sql.selectOne("Approval.cnt", param);
    }

    public SubCntDTO subCnt (String param) {
        return sql.selectOne("Approval.subCnt", param);
    }

    public SubCntDTO personalSubCnt (String param) {
        return sql.selectOne("Approval.personalSubCnt", param);
    }

    public ViewDTO view (Long id) {
        return sql.selectOne("Approval.view", id);
    }

    public List<ChatDTO> chat (Long id) {
        return sql.selectList("Approval.chat", id);
    }

    public ChatAjaxDTO chatOne (String chatid) {
        return sql.selectOne("Approval.chatOne", chatid);
    }

    public int chatSave (ChatAjaxDTO chatAjax) {
        return sql.insert("Approval.chatSave", chatAjax);
    }

    public List<ApproverDTO> approver () {
        return sql.selectList("Approval.approver");
    }

    public List<ApproverDTO> viewer () {
        return sql.selectList("Approval.viewer");
    }
}
