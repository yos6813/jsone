package com.jsone.approval.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jsone.approval.dto.ListDTO;
import com.jsone.approval.dto.LoginDTO;
import com.jsone.approval.repository.ApprovalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalService {
	
	private final ApprovalRepository approvalRepository;
	
	public void loginProcess (LoginDTO loginDTO) {
		approvalRepository.loginProcess(loginDTO);
	}

	public List<ListDTO> list () {
		return approvalRepository.list();
	}

}
