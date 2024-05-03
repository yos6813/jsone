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

import com.jsone.approval.dto.ApproverDTO;
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
	
	/* 로그인 */
	@GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        return "index";
    }
	
	@PostMapping("/login")
	public String loginProcess(@RequestParam Map<String, String> map, HttpServletRequest request, Model model) {
		LoginDTO login = approvalService.loginProcess(map);

		if(login == null) {
			model.addAttribute("loginError", "ID 또는 비밀번호가 일치하지 않습니다.");

			return "index";
        } else {
			CustDTO cust = approvalService.customer(login.getCustid());

			Map<String, String> user = new HashMap<String, String>();
			user.put("dbname", cust.getDb_nm());
			user.put("empid", login.getEmpid());

			approvalService.use(cust.getDb_nm());

			UserDTO userDTO = approvalService.user(user);

			HttpSession session = request.getSession();

			session.setAttribute("manager_nm", cust.getManager_nm());
			session.setAttribute("empid", login.getEmpid());
			session.setAttribute("emp_nm", userDTO.getEmp_nm());
			session.setMaxInactiveInterval(-1);

			return "redirect:/dashboard";
		}
	}

	/* 로그아웃 */
	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if(session != null) {
			session.invalidate();
		}

		approvalService.use("garam_common");
		
		return "redirect:/";
	}
	
	/* 비밀번호 재설정 */
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
	
	/* 대시보드 */
	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpServletRequest request) {
		
		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);

		return "dashboard";
	}

	/* 전자결재 */
	@GetMapping("/sign")
	public String sign(@RequestParam Map<String, String> map, @ModelAttribute ListDTO listDTO, Model model, HttpServletRequest request) {
		model.addAttribute("title", "전자결재");
		model.addAttribute("map", map);

		/* 진행중 문서 */
		map.put("type_cd", "'002'");

		List<UserDTO> users = approvalService.userAll();
		model.addAttribute("users", users);

		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);

		return "list";
	}

	/* 결재문서 */
	@GetMapping("/signDoc")
	public String signDoc(@RequestParam Map<String, String> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "결재문서");
		model.addAttribute("map", map);

		map.put("type_cd", "'003','999'");

		List<UserDTO> users = approvalService.userAll();
		model.addAttribute("users", users);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	/* 공람확인 */
	@GetMapping("/announcementCheck")
	public String announcementCheck(@RequestParam Map<String, String> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "공람확인");
		model.addAttribute("map", map);

		map.put("type_cd", "002");

		List<UserDTO> users = approvalService.userAll();
		model.addAttribute("users", users);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	/* 공람문서 */
	@GetMapping("/announcementDoc")
	public String announcementDoc(@RequestParam Map<String, String> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "공람문서");
		model.addAttribute("map", map);

		map.put("type_cd", "'003','999'");

		List<UserDTO> users = approvalService.userAll();
		model.addAttribute("users", users);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	/* 개인서류 */
	@GetMapping("/personalDoc")
	public String personalDoc(@RequestParam Map<String, String> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "개인서류");
		model.addAttribute("map", map);

		HttpSession session = request.getSession();
		String empid = (String) session.getAttribute("empid");

		map.put("empid", empid);
		
		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);

		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);

		return "list";
	}

	/* 진행서류 */
	@GetMapping("/prograssDoc")
	public String prograssDoc(@RequestParam Map<String, String> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "진행서류");
		model.addAttribute("map", map);

		map.put("type_cd", "'002'");

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);
		
		List<ListDTO> listDTOList = approvalService.list(map);
        model.addAttribute("approvalList", listDTOList);
		
		return "list";
	}

	/* 뷰페이지 */
	@GetMapping("/{id}")
	public String view(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		ViewDTO view = approvalService.view(id);
		List<ChatDTO> chat = approvalService.chat(id);
		List<ApproverDTO> approver = approvalService.approver();
		List<ApproverDTO> viewer = approvalService.viewer();

		model.addAttribute("approver", approver);
		model.addAttribute("viewer", viewer);
		model.addAttribute("view", view);
		model.addAttribute("chatList", chat);
		model.addAttribute("docid", id);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request);
		
		return "view";
	}
	
	/* 편집 페이지 */
	@GetMapping("/edit")
	public String edit() {


		return "edit";
	}
	
}
