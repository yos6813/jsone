package com.jsone.approval.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jsone.approval.dto.ListDTO;
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
	public String sign(@ModelAttribute ListDTO listDTO, Model model) {
		model.addAttribute("title", "전자결재");
		List<ListDTO> listDTOList = approvalService.list();
        model.addAttribute("approvalList", listDTOList);
		return "list";
	}

	@GetMapping("/signDoc")
	public String signDoc(Model model) {
		model.addAttribute("title", "결재문서");
		return "list";
	}

	@GetMapping("/announcementCheck")
	public String announcementCheck(Model model) {
		model.addAttribute("title", "공람확인");
		return "list";
	}

	@GetMapping("/announcementDoc")
	public String announcementDoc(Model model) {
		model.addAttribute("title", "공람문서");
		return "list";
	}

	@GetMapping("/personalDoc")
	public String personalDoc(Model model) {
		model.addAttribute("title", "개인서류");
		return "list";
	}

	@GetMapping("/prograssDoc")
	public String prograssDoc(Model model) {
		model.addAttribute("title", "진행서류");
		return "list";
	}

	@GetMapping("/view")
	public String view() {
		return "view";
	}
	
	@GetMapping("/edit")
	public String edit() {
		return "edit";
	}
	
}
