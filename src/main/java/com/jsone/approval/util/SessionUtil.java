package com.jsone.approval.util;

import org.springframework.ui.Model;

import com.jsone.approval.service.ApprovalService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    public void getSession(Model model, HttpServletRequest request, ApprovalService service) {
        HttpSession session = request.getSession();

        if(session == null) {
            model.addAttribute("error", "로그인 정보가 없습니다.");
        } else {
            model.addAttribute("manager_nm", session.getAttribute("manager_nm"));
            model.addAttribute("name", session.getAttribute("emp_nm"));
            model.addAttribute("empid", session.getAttribute("empid"));
            model.addAttribute("loginid", session.getAttribute("loginid"));
            model.addAttribute("dbName", session.getAttribute("dbName"));
            model.addAttribute("coopCd", session.getAttribute("coop_cd")); //공람자 코드
            model.addAttribute("posCd", session.getAttribute("pos_cd")); //결재자 코드
        }
    }
}
