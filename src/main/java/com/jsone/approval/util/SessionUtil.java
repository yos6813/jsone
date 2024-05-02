package com.jsone.approval.util;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    public void getSession(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        if(session == null) {
            model.addAttribute("error", "로그인 정보가 없습니다.");
        } else {
            model.addAttribute("manager_nm", session.getAttribute("manager_nm"));
            model.addAttribute("name", session.getAttribute("emp_nm"));
            model.addAttribute("empid", session.getAttribute("empid"));
        }
    }
}
