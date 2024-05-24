package com.jsone.approval.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
import com.jsone.approval.repository.ApprovalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalService {
	
	private final ApprovalRepository approvalRepository;
	
	public LoginDTO loginProcess (Map<String, String> param) {
		return approvalRepository.loginProcess(param);
	}

	public LoginDTO authCheck (String loginid) {
		return approvalRepository.authCheck(loginid);
	}

	public Long findUser (Map<String, Object> map) {
		return approvalRepository.findUser(map);
	}

	public void resetPw (Map<String, Object> map) {
		approvalRepository.resetPw(map);
	}

	public void setAuth (Map<String, Object> map) {
		approvalRepository.setAuth(map);
	}

	public void use (String dbname) {
		approvalRepository.use(dbname);
	}

	public CustDTO customer (Long custid) {
		return approvalRepository.customer(custid);
	}

	public UserDTO user (Map<String, String> param) {
		return approvalRepository.user(param);
	}

	public List<UserDTO> userAll () {
		return approvalRepository.userAll();
	}

	public List<ListDTO> list (Map<String, String> param) {
		return approvalRepository.list(param);
	}

	public Map<String, String> cnt (Map<String, String> param) {
		return approvalRepository.cnt(param);
	}

	public SubCntDTO subCnt (Map<String, String> param) {
		return approvalRepository.subCnt(param);
	}

	public SubCntDTO progressSubCnt (Map<String, String> param) {
		return approvalRepository.progressSubCnt(param);
	}

	public SubCntDTO publicSubCnt (Map<String, String> param) {
		return approvalRepository.publicSubCnt(param);
	}

	public SubCntDTO stepSubCnt (Map<String, String> param) {
		return approvalRepository.stepSubCnt(param);
	}

	public SubCntDTO personalSubCnt (String param) {
		return approvalRepository.personalSubCnt(param);
	}

	public ViewDTO view (Long id) {
		return approvalRepository.view(id);
	}

	public List<ChatDTO> chat (Long id) {
		return approvalRepository.chat(id);
	}

	public ChatAjaxDTO chatOne (String chatid) {
		return approvalRepository.chatOne(chatid);
	}

	public int chatSave (ChatAjaxDTO chatAjax) {
		return approvalRepository.chatSave(chatAjax);
	}

	public List<ApproverDTO> approver (Long id) {
		return approvalRepository.approver(id);
	}

	public List<ApproverDTO> viewer (Long id) {
		return approvalRepository.viewer(id);
	}

	public List<ApproverDTO> allApprover (String empid) {
		return approvalRepository.allApprover(empid);
	}

	public List<ApproverDTO> allViewer (String empid) {
		return approvalRepository.allViewer(empid);
	}

	public List<Long> docApprover (Long id) {
		return approvalRepository.docApprover(id);
	}

	public List<Long> docViewer (Long id) {
		return approvalRepository.docViewer(id);
	}

	public void viewerCheck (Map<String, String> map) {
		approvalRepository.viewerCheck(map);
	}

	public Long checkAppov (Map<String, String> map){
		return approvalRepository.checkAppov(map);
	}

	public void docCollect (Map<String, String> map) {
		approvalRepository.docCollect(map);
	}

	public void docCheck (Map<String, String> map) {
		approvalRepository.docCheck(map);
	}

	public void docRefer (Map<String, String> map) {
		approvalRepository.docRefer(map);
	}

	public List<FileDTO> file (Long id) {
		return approvalRepository.file(id);
	}

	public void update (Map<String, String> map) {
		approvalRepository.update(map);
	}

	public void fileUpdate(Map<String, String> map) {
		approvalRepository.fileUpdate(map);
	}

	public List<CodeDTO> checkSeq(String code) {
		return approvalRepository.checkSeq(code);
	}

	public void insertApprover(Map<String, String> map) {
		approvalRepository.insertApprover(map);
	}

	public void insertViewer(Map<String, String> map) {
		approvalRepository.insertViewer(map);
	}

	public void deleteApprover(Long id) {
		approvalRepository.deleteApprover(id);
	}

	public void deleteViewer(Long id) {
		approvalRepository.deleteViewer(id);
	}

	public Map<String, String> checkCd (Long empid) {
		return approvalRepository.checkCd(empid);
	}

	public Long checkStep (Map<String, String> map) {
		return approvalRepository.checkStep(map);
	}

	public Long checkStepLine (Map<String, String> map) {
        return approvalRepository.checkStepLine(map);
    }

	public void approvalDoc (Map<String, String> map){
		approvalRepository.approvalDoc(map);
	}

	public void changeStep (Long id){
		approvalRepository.changeStep(id);
	}

	public void delDoc (Long id){
		approvalRepository.delDoc(id);
	}

	public void delAttach (Long id){
		approvalRepository.delAttach(id);
	}

	public void delApproval (Long id){
		approvalRepository.delApproval(id);
	}

	public void delViewer (Long id){
		approvalRepository.delViewer(id);
	}

	public List<String> checkLoginid (Map<String, String> map) {
		return approvalRepository.checkLoginid(map);
	}

	public List<String> nextApprover (Map<String, String> map) {
		return approvalRepository.nextApprover(map);
	}
}
