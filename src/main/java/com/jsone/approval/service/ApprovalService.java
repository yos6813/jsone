package com.jsone.approval.service;

import org.springframework.stereotype.Service;

import com.jsone.approval.dto.LoginDTO;
import com.jsone.approval.repository.ApprovalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalService {
	
	private final ApprovalRepository approvalRepository;
	
	public void loginProcess (LoginDTO loginDTO) {
		
	}

}
