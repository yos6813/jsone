package com.jsone.approval.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsone.approval.dto.ChatDTO;
import com.jsone.approval.dto.ListDTO;
import com.jsone.approval.dto.LoginDTO;
import com.jsone.approval.dto.ViewDTO;
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
	public String sign(@RequestParam Map<String, String> map, @ModelAttribute ListDTO listDTO, Model model) {
		model.addAttribute("title", "전자결재");
		model.addAttribute("map", map);

		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	@GetMapping("/signDoc")
	public String signDoc(@RequestParam Map<String, String> map, Model model) {
		model.addAttribute("title", "결재문서");
		model.addAttribute("map", map);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	@GetMapping("/announcementCheck")
	public String announcementCheck(@RequestParam Map<String, String> map, Model model) {
		model.addAttribute("title", "공람확인");
		model.addAttribute("map", map);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	@GetMapping("/announcementDoc")
	public String announcementDoc(@RequestParam Map<String, String> map, Model model) {
		model.addAttribute("title", "공람문서");
		model.addAttribute("map", map);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	@GetMapping("/personalDoc")
	public String personalDoc(@RequestParam Map<String, String> map, Model model) {
		model.addAttribute("title", "개인서류");
		model.addAttribute("map", map);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	@GetMapping("/prograssDoc")
	public String prograssDoc(@RequestParam Map<String, String> map, Model model) {
		model.addAttribute("title", "진행서류");
		model.addAttribute("map", map);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);
		
		return "list";
	}

	@GetMapping("/{id}")
	public String view(@PathVariable Long id, Model model) {
		ViewDTO view = approvalService.view(id);
		List<ChatDTO> chat = approvalService.chat(id);

		model.addAttribute("view", view);
		model.addAttribute("chatList", chat);
		
		return "view";
	}
	
	@GetMapping("/edit")
	public String edit() {
		return "edit";
	}
	
}
