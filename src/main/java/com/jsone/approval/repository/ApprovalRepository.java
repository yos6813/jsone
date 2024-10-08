package com.jsone.approval.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jsone.approval.dto.ApproverDTO;
import com.jsone.approval.dto.ChatAjaxDTO;
import com.jsone.approval.dto.ChatDTO;
import com.jsone.approval.dto.CodeDTO;
import com.jsone.approval.dto.CustDTO;
import com.jsone.approval.dto.FileDTO;
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

    public void setAuth (Map<String, Object> map) {
        sql.update("Approval.setAuth", map);
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

    public Map<String, String> cnt (Map<String, String> param) {
        return sql.selectOne("Approval.cnt", param);
    }

    public SubCntDTO subCnt (Map<String, String> param) {
        return sql.selectOne("Approval.subCnt", param);
    }

    public SubCntDTO progressSubCnt (Map<String, String> param) {
        return sql.selectOne("Approval.progressSubCnt", param);
    }

    public SubCntDTO publicSubCnt (Map<String, String> param) {
        return sql.selectOne("Approval.publicSubCnt", param);
    }

    public SubCntDTO stepSubCnt (Map<String, String> param) {
        return sql.selectOne("Approval.stepSubCnt", param);
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

    public List<ApproverDTO> approver (Long id) {
        return sql.selectList("Approval.approver", id);
    }

    public List<ApproverDTO> viewer (Long id) {
        return sql.selectList("Approval.viewer", id);
    }

    public List<ApproverDTO> allApprover (String empid) {
        return sql.selectList("Approval.allApprover", empid);
    }

    public List<ApproverDTO> allViewer (String empid) {
        return sql.selectList("Approval.allViewer", empid);
    }

    public List<Long> docApprover (Long id) {
        return sql.selectList("Approval.docApprover", id);
    }

    public List<Long> docViewer (Long id) {
        return sql.selectList("Approval.docViewer", id);
    }

    public void viewerCheck (Map<String, String> map) {
        sql.update("Approval.viewerCheck", map);
    }

    public Long checkAppov(Map<String, String> map) {
        return sql.selectOne("Approval.checkAppov", map);
    }

    public void docCollect (Map<String, String> map) {
        sql.update("Approval.docCollect", map);
    }

    public void docCheck (Map<String, String> map) {
        sql.update("Approval.docCheck", map);
    }

    public void docRefer (Map<String, String> map) {
        sql.update("Approval.docRefer", map);
    }

    public List<FileDTO> file (Long id) {
        return sql.selectList("Approval.file", id);
    }

    public void update (Map<String, String> map) {
        sql.update("Approval.update", map);
    }
    
    public void insert (Map<String, String> map) {
        sql.update("Approval.insert", map);
    }
    
    public void returnKeep (Map<String, String> map) {
        sql.update("Approval.returnKeep", map);
    }
    
    public Long nextDocid () {
    	return sql.selectOne("Approval.nextDocid");
    }
    
    public void fileUpdate (Map<String, String> map) {
        sql.update("Approval.fileUpdate", map);
    }

    public List<CodeDTO> checkSeq (String code) {
        return sql.selectList("Approval.checkSeq", code);
    }

    public void insertApprover (Map<String, String> map) {
        sql.insert("Approval.insertApprover", map);
    }

    public void insertViewer (Map<String, String> map) {
        sql.insert("Approval.insertViewer", map);
    }

    public void deleteApprover (Long id) {
        sql.delete("Approval.deleteApprover", id);
    }

    public void deleteViewer (Long id) {
        sql.delete("Approval.deleteViewer", id);
    }

    public Map<String, String> checkCd (Long empid) {
        return sql.selectOne("Approval.checkCd", empid);
    }

    public Long checkStep (Map<String, String> map) {
        return sql.selectOne("Approval.checkStep", map);
    }

    public Long checkStepLine (Map<String, String> map) {
        return sql.selectOne("Approval.checkStepLine", map);
    }

    public void approvalDoc (Map<String, String> map) {
        sql.update("Approval.approvalDoc", map);
    }

    public void changeStep (Long id) {
        sql.update("Approval.changeStep", id);
    }

    public void delDoc (Long id) {
        sql.update("Approval.delDoc", id);
    }

    public void delAttach (Long id) {
        sql.update("Approval.delAttach", id);
    }

    public void delOneAttach (Long attachid) {
        sql.update("Approval.delOneAttach", attachid);
    }

    public void delApproval (Long id) {
        sql.update("Approval.delApproval", id);
    }

    public void delViewer (Long id) {
        sql.update("Approval.delViewer", id);
    }

    public List<String> checkLoginid (Map<String, String> map) {
        return sql.selectList("Approval.checkLoginid", map);
    }

    public List<String> nextApprover (Map<String, String> map) {
        return sql.selectList("Approval.nextApprover", map);
    }
}