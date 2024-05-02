package com.jsone.approval.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jsone.approval.dto.ChatAjaxDTO;
import com.jsone.approval.service.ApprovalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AjaxController {

    private final ApprovalService approvalService;

	/* 뷰 하단 대화 */
    @PostMapping("/saveChat")
	@ResponseBody
	public ChatAjaxDTO saveChat(@RequestBody ChatAjaxDTO chatAjax) {
		approvalService.chatSave(chatAjax);
        String chatid = chatAjax.getChatid();

		ChatAjaxDTO chat = approvalService.chatOne(chatid);

		return chat;
	}
}
