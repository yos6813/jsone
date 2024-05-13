package com.jsone.approval.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.jsone.approval.dto.ChatAjaxDTO;
import com.jsone.approval.dto.ListDTO;
import com.jsone.approval.service.ApprovalService;
import com.jsone.approval.util.SmsUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AjaxController {

    private final ApprovalService approvalService;

	@PostMapping("/sendAuth")
	@ResponseBody
    public ResponseEntity<String> sendAuth(RestTemplate restTemplate, @RequestBody Map<String, String> map, HttpServletRequest request) {
		SmsUtil sms = new SmsUtil(restTemplate);
		int randNum = (int) (Math.random() * 10000);

		HttpSession session = request.getSession();

		session.setAttribute("authNum", randNum);

		Map<String, String> smsData = new HashMap<>();
        smsData.put("user_id", "jsoftone");
        smsData.put("key", "xg7d36hj0xavo5a40vq98ch7pu9339za");
        smsData.put("msg", "인증번호[" + randNum + "] 가람웹 로그인 인증 문자입니다. 웹화면에서 인증번호를 입력해주세요.");
        smsData.put("receiver", "01039196562");
        smsData.put("destination", "");
        smsData.put("sender", "0220384812");
        smsData.put("testmode_yn", "Y");
        smsData.put("title", "본인인증");

		// SMS 전송 요청 보내기
        ResponseEntity<String> response = sms.sendSms(smsData);

		System.out.println("SMS 전송 결과: " + response.getBody());
		System.out.println("인증번호: " + session.getAttribute("authNum"));

        return sms.sendSms(smsData);
    }

	@PostMapping("/authCheck")
	@ResponseBody
	public Map<String, String> authCheck(@RequestBody Map<String, String> map, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, String> result  = new HashMap<String, String>();

		System.out.println("입력번호: " + map.get("authNum"));

		if(map.get("authNum").equals(session.getAttribute("authNum").toString())) {
			result.put("status", "success");
			result.put("msg", "인증이 완료되었습니다. 비밀번호를 설정해주세요.");
		} else {
			result.put("status", "error");
			result.put("msg", "인증번호가 틀립니다.");
		}
		
		return result;
	}
	

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

	/* edit 페이지 내 파일 삭제 */
	@PostMapping("/deleteFile")
	@ResponseBody
	public Map<String, String> deleteFile(@RequestParam String fileName, HttpServletRequest request) {
		String uploadPath = request.getServletContext().getRealPath("/files");
		String filePath = uploadPath + File.separator + fileName;

		Map<String, String> map = new HashMap<String, String>();

		File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
				map.put("status", "success");
				map.put("msg", "파일을 삭제하였습니다.");

                return map;
            } else {
				map.put("status", "error");
				map.put("msg", "파일을 삭제하지 못했습니다.");

                return map;
            }
        } else {
            map.put("status", "error");
			map.put("msg", "파일경로가 올바르지 않습니다.");

			return map;
        }
	}
	
}
