package com.jsone.approval.controller;

import java.util.HashMap;
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
import com.jsone.approval.dto.CustDTO;
import com.jsone.approval.dto.ListDTO;
import com.jsone.approval.dto.LoginDTO;
import com.jsone.approval.dto.UserDTO;
import com.jsone.approval.dto.ViewDTO;
import com.jsone.approval.service.ApprovalService;
import com.jsone.approval.util.SessionUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final ApprovalService approvalService;
	
	@GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();

		if(session != null) {
			model.addAttribute("error", session.getAttribute("error_message"));
		}

        return "index";
    }
	
	@PostMapping("/login")
	public String loginProcess(@RequestParam Map<String, String> map, HttpServletRequest request, Model model) {
		LoginDTO login = approvalService.loginProcess(map);

		if(login == null) {
			model.addAttribute("loginError", "ID 또는 비밀번호가 틀렸습니다.");

			return "index";
        } else {
			CustDTO cust = approvalService.customer(login.getCustid());

			Map<String, String> user = new HashMap<String, String>();
			user.put("dbname", cust.getDb_nm());
			user.put("empid", login.getEmpid());

			UserDTO userDTO = approvalService.user(user);

			HttpSession session = request.getSession();

			session.setAttribute("manager_nm", cust.getManager_nm());
			session.setAttribute("empid", login.getEmpid());
			session.setAttribute("emp_nm", userDTO.getEmp_nm());
			session.setMaxInactiveInterval(-1);

			approvalService.use(cust.getDb_nm());

			return "redirect:/dashboard";
		}
	}

	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if(session != null) {
			session.invalidate();
		}
		
		return "redirect:/";
	}
	

	@GetMapping("/resetPassword")
	public String resetPassword() {
		return "resetPassword";
	}

	@PostMapping("/resetPassword")
	public String postMethodName(@RequestParam Map<String, Object> map, Model model) {
		Long findUser = approvalService.findUser(map);

		if(findUser != null) {
			approvalService.resetPw(map);

			return "redirect:/";
		} else {
			model.addAttribute("error", "없는 사용자입니다.");

			return "resetPassword";
		}
	}
	
	
	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpServletRequest request) {
		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);

		return "dashboard";
	}

	@GetMapping("/sign")
	public String sign(@RequestParam Map<String, Object> map, @ModelAttribute ListDTO listDTO, Model model, HttpServletRequest request) {
		model.addAttribute("title", "전자결재");
		model.addAttribute("map", map);

		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);

		return "list";
	}

	@GetMapping("/signDoc")
	public String signDoc(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "결재문서");
		model.addAttribute("map", map);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	@GetMapping("/announcementCheck")
	public String announcementCheck(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "공람확인");
		model.addAttribute("map", map);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	@GetMapping("/announcementDoc")
	public String announcementDoc(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "공람문서");
		model.addAttribute("map", map);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	/* 개인서류 */
	@GetMapping("/personalDoc")
	public String personalDoc(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "개인서류");
		model.addAttribute("map", map);
		
		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);

		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	@GetMapping("/prograssDoc")
	public String prograssDoc(@RequestParam Map<String, Object> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "진행서류");
		model.addAttribute("map", map);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);
		
		return "list";
	}

	@GetMapping("/{id}")
	public String view(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		ViewDTO view = approvalService.view(id);
		List<ChatDTO> chat = approvalService.chat(id);

		model.addAttribute("view", view);
		model.addAttribute("chatList", chat);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);
		
		return "view";
	}
	
	@GetMapping("/edit")
	public String edit() {
		return "edit";
	}
	
}
