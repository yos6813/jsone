package com.jsone.approval.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jsone.approval.dto.LoginDTO;
import com.jsone.approval.service.ApprovalService;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final ApprovalService approvalService;
	
	@GetMapping("/")
    public String index() {
        return "index";
    }
	
	@PostMapping("/login")
	public String loginProcess(@ModelAttribute LoginDTO loginDTO) {
		System.out.println("loginDTO = " + loginDTO);
		approvalService.loginProcess(loginDTO);
        return "redirect:/dashboard";
	}

	@GetMapping("/resetPassword")
	public String resetPassword() {
		return "resetPassword";
	}
	
	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

}
