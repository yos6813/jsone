package com.jsone.approval.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@GetMapping("/sign")
	public String sign(Model model) {
		model.addAttribute("title", "전자결재");
		return "sign";
	}

	@GetMapping("/signDoc")
	public String signDoc(Model model) {
		model.addAttribute("title", "결재문서");
		return "sign";
	}

	@GetMapping("/announcementCheck")
	public String announcementCheck(Model model) {
		model.addAttribute("title", "공람확인");
		return "sign";
	}

	@GetMapping("/announcementDoc")
	public String announcementDoc(Model model) {
		model.addAttribute("title", "공람문서");
		return "sign";
	}

	@GetMapping("/personalDoc")
	public String personalDoc(Model model) {
		model.addAttribute("title", "개인서류");
		return "sign";
	}

	@GetMapping("/prograssDoc")
	public String prograssDoc(Model model) {
		model.addAttribute("title", "진행서류");
		return "prograssDoc";
	}

}
