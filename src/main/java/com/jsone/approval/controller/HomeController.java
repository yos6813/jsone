package com.jsone.approval.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsone.approval.dto.LoginDTO;
import com.jsone.approval.service.ApprovalService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final ApprovalService approvalService;
	
	@GetMapping("/")
    public String index() {
        System.out.println("HomeController.index");
        return "index";
    }
	
	@PostMapping("/login")
	public String loginProcess(@ModelAttribute LoginDTO loginDTO) {
		System.out.println("loginDTO = " + loginDTO);
		approvalService.loginProcess(loginDTO);
        return "redirect:/dashboard";
	}

}
