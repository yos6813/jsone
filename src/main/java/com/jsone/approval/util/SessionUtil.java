package com.jsone.approval.util;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    public void getSession(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        model.addAttribute("manager_nm", session.getAttribute("manager_nm"));
		model.addAttribute("name", session.getAttribute("emp_nm"));
    }
}
