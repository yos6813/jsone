package com.jsone.approval.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jsone.approval.dto.ChatDTO;
import com.jsone.approval.dto.ListDTO;
import com.jsone.approval.dto.LoginDTO;
import com.jsone.approval.dto.ViewDTO;
import com.jsone.approval.repository.ApprovalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalService {
	
	private final ApprovalRepository approvalRepository;
	
	public void loginProcess (LoginDTO loginDTO) {
		approvalRepository.loginProcess(loginDTO);
	}

	public List<ListDTO> list (Map<String, String> param) {
		return approvalRepository.list(param);
	}

	public ViewDTO view (Long id) {
		return approvalRepository.view(id);
	}

	public List<ChatDTO> chat (Long id) {
		return approvalRepository.chat(id);
	}

}
