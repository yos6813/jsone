package com.jsone.approval.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsone.approval.dto.ChatAjaxDTO;
import com.jsone.approval.dto.ListDTO;
import com.jsone.approval.service.ApprovalService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AjaxController {

    private final ApprovalService approvalService;

	@PostMapping("/viewerCheck")
	@ResponseBody
	public Map<String, String> viewerCheck(@RequestBody Map<String, String> map) {
		approvalService.viewerCheck(map);

		Map<String, String> result = new HashMap<String, String>();

		result.put("result", "success");

		return result;
	}

	@PostMapping("/docCollect")
	public Map<String, String> docCollect(@RequestBody Map<String, String> map) {
		approvalService.docCollect(map);

		Map<String, String> result = new HashMap<String, String>();

		result.put("result", "success");
		
		return result;
	}

	@PostMapping("/docCheck")
	public Map<String, String> docCheck(@RequestBody Map<String, String> map) {
		approvalService.docCheck(map);

		Map<String, String> result = new HashMap<String, String>();

		result.put("result", "success");
		
		return result;
	}

	@PostMapping("/docRefer")
	public Map<String, String> docRefer(@RequestBody Map<String, String> map) {
		approvalService.docRefer(map);

		Map<String, String> result = new HashMap<String, String>();

		result.put("result", "success");
		
		return result;
	}
	
	/* 파일 업로드 */
	@PostMapping("/fileUpload")
	@ResponseBody
	public Map<String, String> fileUpload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		String fileName = multipartFile.getOriginalFilename();
		Long size = multipartFile.getSize();
		double sizeMB = size / (1024.0 * 1024.0);
		String uploadPath = request.getServletContext().getRealPath("/files");
		String filePath = uploadPath + File.separator + fileName;

		try {
			File dest = new File(filePath);
			multipartFile.transferTo(dest); // 파일을 저장합니다.
			map.put("status", "success");
			map.put("filePath", filePath);
			map.put("fileName", fileName);
			map.put("size", Double.toString(sizeMB));
		} catch (IOException e) {
			e.printStackTrace();
			map.put("status", "error");
		}

		return map;
	}

	/* 뷰 하단 대화 */
    @PostMapping("/saveChat")
	@ResponseBody
	public ChatAjaxDTO saveChat(@RequestBody ChatAjaxDTO chatAjax) {
		approvalService.chatSave(chatAjax);
        String chatid = chatAjax.getChatid();

		ChatAjaxDTO chat = approvalService.chatOne(chatid);

		return chat;
	}

	/* 리스트 페이지네이션 */
	@PostMapping("/pagination")
	@ResponseBody
	public List<ListDTO> pagination(Model model, @RequestBody Map<String, String> map) {
		List<ListDTO> listDTOList = approvalService.list(map);
		
		return listDTOList;
	}
}
