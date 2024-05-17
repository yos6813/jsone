package com.jsone.approval.controller;

import java.io.File;
import java.util.ArrayList;
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
import com.jsone.approval.dto.FileDTO;
import com.jsone.approval.dto.ListDTO;
import com.jsone.approval.dto.LoginDTO;
import com.jsone.approval.dto.SubCntDTO;
import com.jsone.approval.dto.UserDTO;
import com.jsone.approval.dto.ViewDTO;
import com.jsone.approval.service.ApprovalService;
import com.jsone.approval.util.CommonUtil;
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
		HttpSession session = request.getSession(false);

		if(session != null) {
			session.invalidate();
		}

		approvalService.use("garam_common");
		
        return "index";
    }
	
	@PostMapping("/login")
	public String loginProcess(@RequestParam Map<String, String> map, HttpServletRequest request, Model model) {
		LoginDTO login = approvalService.loginProcess(map);

		if(login == null) {
			LoginDTO authCheck = approvalService.authCheck(map.get("loginid"));

			if(authCheck != null) {
				model.addAttribute("loginError", "ID 또는 비밀번호가 일치하지 않습니다.");
			} else {
				model.addAttribute("loginError", "인증되지 않은 회원입니다. 인증을 진행해주세요.");
				model.addAttribute("errorType", "auth");
				model.addAttribute("loginid", map.get("loginid"));
			}

			return "index";
        } else {
			if(login.getAuthyn() == 'N') {
				model.addAttribute("loginError", "인증되지 않은 회원입니다. 인증을 진행해주세요.");
				model.addAttribute("errorType", "auth");
				model.addAttribute("loginid", map.get("loginid"));

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
				session.setAttribute("empid", Long.parseLong(login.getEmpid()));
				session.setAttribute("loginid", login.getLoginid());
				session.setAttribute("emp_nm", userDTO.getEmp_nm());
				session.setAttribute("dbName", cust.getDb_nm());
				session.setAttribute("coop_cd", userDTO.getPos_cd());
				session.setAttribute("pos_cd", userDTO.getCoop_cd());
				session.setMaxInactiveInterval(-1);

				return "redirect:/dashboard";
			}
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
	public String postResetPassword(@RequestParam Map<String, Object> map, Model model) {
		Long findUser = approvalService.findUser(map);

		if(findUser != null) {
			approvalService.resetPw(map);

			return "redirect:/";
		} else {
			model.addAttribute("error", "없는 사용자입니다.");

			return "resetPassword";
		}
	}

	/* 비밀번호 설정 */
	@PostMapping("/setPassword")
	public String setPassword(@RequestParam Map<String, Object> map, Model model) {
		approvalService.setAuth(map);

		return "redirect:/";
	}
	
	/* 대시보드 */
	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpServletRequest request) {
		
		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request, approvalService);
		if(model.getAttribute("error") == null) {
			if(model.getAttribute("dbName") != null) {
				approvalService.use(model.getAttribute("dbName").toString());
				return "dashboard";
			} else {
				return "redirect:/";
			}
			
		} else {
			return "redirect:/";
		}
	}

	/* 전자결재 */
	@GetMapping("/sign")
	public String sign(@RequestParam Map<String, String> map, @ModelAttribute ListDTO listDTO, Model model, HttpServletRequest request) {
		model.addAttribute("title", "전자결재");
		model.addAttribute("map", map);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request, approvalService);

		if(model.getAttribute("error") != "") {
			if(model.getAttribute("dbName") != null) {
				approvalService.use(model.getAttribute("dbName").toString());

				/* 진행중 문서 */
				map.put("type_cd", "'002'");
				map.put("code", model.getAttribute("coopCd").toString());
				map.put("title", "결재");

				/* 현재 필터링 되고 있는 서브메뉴 */
				if(map.get("status_cd") != null) {
					map.put("status", map.get("status_cd"));
				}

				List<UserDTO> users = approvalService.userAll();
				model.addAttribute("users", users);

				List<ListDTO> listDTOList = approvalService.list(map);
				model.addAttribute("approvalList", listDTOList);

				/* 리스트 개수 */
				Map<String, String> cnt = approvalService.cnt(map);
				model.addAttribute("cnt", cnt);

				/* 서브 메뉴 별 개수 */
				SubCntDTO subCnt = approvalService.stepSubCnt(map);
				model.addAttribute("subCnt", subCnt);
			
				return "list";
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
	}

	/* 결재문서 */
	@GetMapping("/signDoc")
	public String signDoc(@RequestParam Map<String, String> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "결재문서");
		model.addAttribute("map", map);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request, approvalService);
		if(model.getAttribute("error") != "") {
			if(model.getAttribute("dbName") != null) {
				approvalService.use(model.getAttribute("dbName").toString());
				map.put("type_cd", "'003','999','005'");
				map.put("pid", model.getAttribute("empid").toString());
				map.put("title", "결재문서");

				/* 현재 필터링 되고 있는 서브메뉴 */
				if(map.get("status_cd") != null) {
					map.put("status", map.get("status_cd"));
				}

				List<UserDTO> users = approvalService.userAll();
				model.addAttribute("users", users);
				
				List<ListDTO> listDTOList = approvalService.list(map);
				model.addAttribute("approvalList", listDTOList);

				/* 리스트 개수 */
				Map<String, String> cnt = approvalService.cnt(map);
				model.addAttribute("cnt", cnt);

				/* 서브 메뉴 별 개수 */
				SubCntDTO subCnt = approvalService.stepSubCnt(map);
				model.addAttribute("subCnt", subCnt);

				return "list";
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
	}

	/* 공람확인 */
	@GetMapping("/announcementCheck")
	public String announcementCheck(@RequestParam Map<String, String> map, Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("title", "공람확인");
		model.addAttribute("map", map);
		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request, approvalService);
		if(model.getAttribute("error") != "") {
			if(model.getAttribute("dbName") != null) {
				approvalService.use(model.getAttribute("dbName").toString());

				map.put("type_cd", "002");

				List<UserDTO> users = approvalService.userAll();
				model.addAttribute("users", users);
				map.put("code",model.getAttribute("posCd").toString());
				map.put("title", "공람");

				if(model.getAttribute("posCd") == null || model.getAttribute("posCd").toString().isEmpty()) {
					model.addAttribute("approvalList", null);

					model.addAttribute("cnt", null);
					System.out.println("여기");
				} else {
					/* 현재 필터링 되고 있는 서브메뉴 */
					if(map.get("status_cd") != null) {
						map.put("status", map.get("status_cd"));
					}

					List<ListDTO> listDTOList = approvalService.list(map);
					model.addAttribute("approvalList", listDTOList);
					
					/* 리스트 개수 */
					Map<String, String> cnt = approvalService.cnt(map);
					model.addAttribute("cnt", cnt);
				}

				/* 서브 메뉴 별 개수 */
				SubCntDTO subCnt = approvalService.publicSubCnt(map);
				model.addAttribute("subCnt", subCnt);

				return "list";
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
	}

	/* 공람문서 */
	@GetMapping("/announcementDoc")
	public String announcementDoc(@RequestParam Map<String, String> map, Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("title", "공람문서");
		model.addAttribute("map", map);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request, approvalService);
		if(model.getAttribute("error") != "") {
			if(model.getAttribute("dbName") != null) {
				approvalService.use(model.getAttribute("dbName").toString());

				map.put("type_cd", "'003','999','005'");
				map.put("title", "공람문서");
				map.put("pid", model.getAttribute("empid").toString());

				if(model.getAttribute("posCd") == null || model.getAttribute("posCd").toString().isEmpty()) {
					model.addAttribute("approvalList", null);
					model.addAttribute("cnt", null);
				} else {
					/* 현재 필터링 되고 있는 서브메뉴 */
					if(map.get("status_cd") != null) {
						map.put("status", map.get("status_cd"));
					}

					List<ListDTO> listDTOList = approvalService.list(map);
					model.addAttribute("approvalList", listDTOList);

					/* 리스트 개수 */
					Map<String, String> cnt = approvalService.cnt(map);
					model.addAttribute("cnt", cnt);
				}

				List<UserDTO> users = approvalService.userAll();
				model.addAttribute("users", users);

				/* 서브 메뉴 별 개수 */
				SubCntDTO subCnt = approvalService.publicSubCnt(map);
				model.addAttribute("subCnt", subCnt);
				return "list";
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
	}

	/* 개인서류 */
	@GetMapping("/personalDoc")
	public String personalDoc(@RequestParam Map<String, String> map, Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("title", "개인서류");
		model.addAttribute("map", map);
		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request, approvalService);
		if(model.getAttribute("error") != "") {
			if(model.getAttribute("dbName") != null) {
				approvalService.use(model.getAttribute("dbName").toString());

				/* 현재 필터링 되고 있는 서브메뉴 */
				if(map.get("type_cd") != null) {
					String type = map.get("type_cd");
					map.put("type", type);
				}

				String empid = session.getAttribute("empid").toString();

				map.put("empid", empid);

				List<ListDTO> listDTOList = approvalService.list(map);
				model.addAttribute("approvalList", listDTOList);

				/* 리스트 개수 */
				Map<String, String> cnt = approvalService.cnt(map);
				model.addAttribute("cnt", cnt);

				/* 서브 메뉴 별 개수 */
				SubCntDTO subCnt = approvalService.personalSubCnt(empid);
				model.addAttribute("subCnt", subCnt);

				return "list";
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
	}

	/* 진행서류 */
	@GetMapping("/prograssDoc")
	public String prograssDoc(@RequestParam Map<String, String> map, Model model, HttpServletRequest request) {
		model.addAttribute("title", "진행서류");
		model.addAttribute("map", map);

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request, approvalService);
		if(model.getAttribute("error") != "") {
			if(model.getAttribute("dbName") != null) {
				approvalService.use(model.getAttribute("dbName").toString());

				/* 현재 필터링 되고 있는 서브메뉴 */
				if(map.get("status_cd") != null) {
					map.put("status", map.get("status_cd"));
				}

				map.put("type_cd", "'002'");
				map.put("empid", model.getAttribute("empid").toString());
				
				/* 페이지네이션 기본값 */
				map.put("page", "0");
				
				List<ListDTO> listDTOList = approvalService.list(map);
				model.addAttribute("approvalList", listDTOList);

				/* 리스트 개수 */
				Map<String, String> cnt = approvalService.cnt(map);
				model.addAttribute("cnt", cnt);

				/* 서브 메뉴 별 개수 */
				SubCntDTO subCnt = approvalService.subCnt(map);
				model.addAttribute("subCnt", subCnt);

				return "list";
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
	}

	/* 뷰페이지 */
	@GetMapping("/{id}")
	public String view(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request, approvalService);
		if(model.getAttribute("error") != "") {
			if(model.getAttribute("dbName") != null) {
				approvalService.use(model.getAttribute("dbName").toString());
				ViewDTO view = approvalService.view(id);
				List<ChatDTO> chat = approvalService.chat(id);
				List<Long> docApprover = approvalService.docApprover(id);
				List<Long> docViewer = approvalService.docViewer(id);
				List<FileDTO> file = approvalService.file(id);
				List<ApproverDTO> approver = approvalService.approver(id);
				List<ApproverDTO> viewer = approvalService.viewer(id);

				/* 결재자와 공람자 empid 비교를 위해 empid만 따로 저장 */
				List<Long> allApprover = new ArrayList<Long>();
				List<Long> allViewer = new ArrayList<Long>();

				approver.forEach(a -> {
					allApprover.add(a.getEmpid());
				});

				viewer.forEach(v -> {
					allViewer.add(v.getEmpid());
				});

				model.addAttribute("approver", approver);
				model.addAttribute("viewer", viewer);
				model.addAttribute("docApprover", docApprover);
				model.addAttribute("docViewer", docViewer);
				model.addAttribute("allApprover", allApprover);
				model.addAttribute("allViewer", allViewer);
				model.addAttribute("view", view);
				model.addAttribute("chatList", chat);
				model.addAttribute("docid", id);
				model.addAttribute("file", file);
				
				return "view";
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
	}
	
	/* 편집 페이지 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request, approvalService);

		if(model.getAttribute("error") != "") {
			if(model.getAttribute("dbName") != null) {
				approvalService.use(model.getAttribute("dbName").toString());
				ViewDTO info = approvalService.view(id);
				List<ApproverDTO> approver = approvalService.approver(id);
				List<ApproverDTO> viewer = approvalService.viewer(id);
				List<FileDTO> file = approvalService.file(id);

				model.addAttribute("approver", approver);
				model.addAttribute("viewer", viewer);
				model.addAttribute("info", info);
				model.addAttribute("id", id);
				model.addAttribute("file", file);

				return "edit";
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
	}
	
	/* 글 저장 */
	@PostMapping("/update")
	public String update(@RequestParam Map<String, String> map, @RequestParam(name = "original_file_name", required = false) String[] fileName, @RequestParam("approver") Long[] approv, @RequestParam("viewer") Long[] view, Model model, HttpServletRequest request) {
		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request, approvalService);

		if("update".equals(map.get("form_type"))) {
			CommonUtil commonUtil = new CommonUtil();
			String contentsText = commonUtil.removeTagString(map.get("contents").toString());

			map.put("contents_text", contentsText);

			approvalService.update(map);

			if(fileName != null){
				for (String item : fileName) {
					String filePath = "/files" + File.separator + item;

					map.put("file_path", filePath);
					map.put("fileName", item);

					approvalService.fileUpdate(map);
				}
			}

			List<Long> approver = approvalService.docApprover(Long.parseLong(map.get("id")));
			List<Long> viewer = approvalService.docViewer(Long.parseLong(map.get("id")));


			Map<String, Long> appList = new HashMap<>();
			appList.put("id", Long.parseLong(map.get("id")));
			if(approver.size() > approv.length) {
				for (Long item : approv) {
					if(approvalService.checkCd(item).get("coop_cd") != null) {
						appList.put("coop_cd", Long.parseLong(approvalService.checkCd(item).get("coop_cd")));
			
						approvalService.deleteApprover(appList);
					}
				}
			}

			Map<String, Long> viewList = new HashMap<>();
			viewList.put("id", Long.parseLong(map.get("id")));
			if(viewer.size() > view.length) {
				for (Long item : view) {
					if(approvalService.checkCd(item).get("pos_cd") != null) {
						viewList.put("pos_cd", Long.parseLong(approvalService.checkCd(item).get("pos_cd")));
				
						approvalService.deleteViewer(viewList);
					}
				}
			}

			return "redirect:/edit/" + map.get("id");
		} else if("approval".equals(map.get("form_type"))) {
			map.put("type_cd", "002");
			approvalService.approvalDoc(map);

			return "redirect:/" + map.get("id");
		} else if("delete".equals(map.get("form_type"))) {
			approvalService.delAttach(Long.parseLong(map.get("id")));
			approvalService.delApproval(Long.parseLong(map.get("id")));
			approvalService.delViewer(Long.parseLong(map.get("id")));
			approvalService.delDoc(Long.parseLong(map.get("id")));

			return "redirect:/personalDoc";
		}

		return "redirect:/edit/" + map.get("id");
	}
}
