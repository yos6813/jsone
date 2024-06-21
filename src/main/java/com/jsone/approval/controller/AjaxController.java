package com.jsone.approval.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
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
import com.jsone.approval.dto.ViewDTO;
import com.jsone.approval.service.ApprovalService;
import com.jsone.approval.util.SessionUtil;
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
        smsData.put("receiver", map.get("telNo"));
        smsData.put("destination", "");
        smsData.put("sender", "0220384812");
        //smsData.put("testmode_yn", "Y");
        smsData.put("title", "본인인증");

		System.out.println(randNum);

        return sms.sendSms(smsData);
    }

	@PostMapping("/sendApproval")
	@ResponseBody
	public ResponseEntity<String> sendApproval(RestTemplate restTemplate, @RequestBody Map<String, String> map, HttpServletRequest request) {
		SmsUtil sms = new SmsUtil(restTemplate);
		String name = map.get("name");
		String title = map.get("title");
		String button = map.get("button");
		String telNo = map.get("telNo");
		String id = map.get("id");

		Map<String, String> param = new HashMap<>();

		param.put("empid", telNo);
		param.put("docid", id);

		List<String> checkLoginid = approvalService.checkLoginid(param);
		Map<String, String> kakaoData = new HashMap<>();

		for(Integer i = 0; i < checkLoginid.size(); i++){
			kakaoData.put("receiver_" + (i + 1), checkLoginid.get(i));
			kakaoData.put("message_" + (i + 1), name + "님의 \"" + title + "\" 전자결재 문서가 도착하였습니다");
			kakaoData.put("emtitle_" + (i + 1), "전자결재 알림서비스");
			kakaoData.put("button_" + (i + 1), button);
		}
		
        kakaoData.put("userid", "jsoftone");
        kakaoData.put("apikey", "xg7d36hj0xavo5a40vq98ch7pu9339za");
		kakaoData.put("senderkey", "7b7f39a82a33c7d0069be9ecb4df9a477df974a6");
		kakaoData.put("tpl_code", "TT_4348"); //템플릿 코드
        kakaoData.put("sender", "0220384812");
        //kakaoData.put("testmode_yn", "Y");

		System.out.println(kakaoData.toString());

		//알림톡 전송 요청 보내기
        ResponseEntity<String> response = sms.sendKakao(kakaoData);

		System.out.println(response.getBody());

        return response;
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

		System.out.println(map);

		Map<String, String> result = new HashMap<String, String>();

		result.put("result", "success");

		return result;
	}

	/* 결재회수 */
	@PostMapping("/docCollect")
	public Map<String, String> docCollect(@RequestBody Map<String, String> map) {
		approvalService.docCollect(map);

		Map<String, String> result = new HashMap<String, String>();

		result.put("result", "success");
		
		return result;
	}

	/* 결재 */
	@PostMapping("/docCheck")
	public Map<String, String> docCheck(@RequestBody Map<String, String> map, RestTemplate restTemplate) {
		SmsUtil sms = new SmsUtil(restTemplate);
		approvalService.docCheck(map);

		Long approvCnt = approvalService.checkAppov(map);
		map.put("id", map.get("docid"));

		if(approvCnt <= 0) {
			map.put("status_cd", "005");
			approvalService.changeStep(Long.parseLong(map.get("docid")));
			approvalService.approvalDoc(map);
		} else {
			Long step = approvalService.checkStep(map);

			map.put("step", step.toString());

			Long stepCnt = approvalService.checkStepLine(map);

			if(stepCnt <= 0) {
				approvalService.changeStep(Long.parseLong(map.get("docid")));
			}

			Map<String, String> param = new HashMap<>();
			param.put("empid", step.toString());
			param.put("docid", map.get("docid"));
			param.put("step", map.get("step").toString());

			List<String> nextApprover = approvalService.nextApprover(param);
			Map<String, String> kakaoData = new HashMap<>();

			ViewDTO view = approvalService.view(Long.parseLong(map.get("docid")));

			for(Integer i = 0; i < nextApprover.size(); i++){
				kakaoData.put("receiver_" + (i + 1), nextApprover.get(i));
				kakaoData.put("message_" + (i + 1), view.getEmp_nm() + "님의 \"" + view.getTitle() + "\" 전자결재 문서가 도착하였습니다");
				kakaoData.put("emtitle_" + (i + 1), "전자결재 알림서비스");
				kakaoData.put("button_" + (i + 1), map.get("button").toString());
			}
			
			kakaoData.put("userid", "jsoftone");
			kakaoData.put("apikey", "xg7d36hj0xavo5a40vq98ch7pu9339za");
			kakaoData.put("senderkey", "7b7f39a82a33c7d0069be9ecb4df9a477df974a6");
			kakaoData.put("tpl_code", "TT_4348"); //템플릿 코드
			kakaoData.put("sender", "0220384812");
			//kakaoData.put("testmode_yn", "Y");

			//알림톡 전송 요청 보내기
			ResponseEntity<String> response = sms.sendKakao(kakaoData);
		}

		Map<String, String> result = new HashMap<String, String>();

		result.put("result", "success");
		
		return result;
	}

	@PostMapping("/docRefer")
	public Map<String, String> docRefer(@RequestBody Map<String, String> map, RestTemplate restTemplate) {
		map.put("id", map.get("docid"));
		map.put("status_cd", "003");
		approvalService.docRefer(map);

		Map<String, String> result = new HashMap<String, String>();

		result.put("result", "success");
		
		return result;
	}
	
	/* 파일 업로드 */
	@PostMapping("/fileUpload")
	@ResponseBody
	public Map<String, String> fileUpload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		Map<String, String> map = new HashMap<String, String>();

		Date date = new Date();
		String fileName = "temp" + date.getTime(); // 파일명을 항상 temp + timemilisecond로 설정
		String originalFname = multipartFile.getOriginalFilename();
		Long size = multipartFile.getSize();
		double sizeMB = size / (1024.0 * 1024.0);
		String ext = originalFname.substring(originalFname.lastIndexOf("."));

		String uploadPath = request.getServletContext().getRealPath("/approval/" + session.getAttribute("custid"));
		String filePath = uploadPath + File.separator + fileName + ext;

        // FTP 서버에 파일 업로드
        String server = "ftp.jsoftone.co.kr";
        int port = 21;
        String user = "upload";
        String pass = "garam@019";

		File dest = new File(filePath);

		// 파일을 먼저 로컬에 저장
        try {
            multipartFile.transferTo(dest); // 파일을 저장합니다.
            map.put("status", "success");
            map.put("filePath", filePath);
            map.put("fileName", fileName + ext);
            map.put("oriFileName", originalFname);
            map.put("size", Double.toString(sizeMB));
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status", "error");
            return map; // 파일 저장 실패 시 바로 반환
        }

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            boolean loggedIn = ftpClient.login(user, pass);
            if (!loggedIn) {
                map.put("status", "error");
                map.put("msg", "FTP 서버에 로그인할 수 없습니다.");
                return map; // FTP 서버 로그인 실패 시 바로 반환
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // FTP 서버에 업로드할 파일 경로 설정
            String remoteFilePath = "/approval/" + session.getAttribute("custid") + "/" + fileName + ext;

            try (InputStream inputStream = new FileInputStream(filePath)) {
                boolean uploaded = ftpClient.storeFile(remoteFilePath, inputStream);
                if (uploaded) {
                    System.out.println("File is uploaded to FTP server successfully.");
					dest.delete();
                } else {
                    map.put("status", "error");
                    map.put("msg", "FTP 서버에 파일을 업로드하지 못했습니다.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                map.put("status", "error");
                map.put("msg", "FTP 서버와의 파일 업로드 중 오류가 발생했습니다.");
            }

            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status", "error");
            map.put("msg", "FTP 서버와의 연결 중 오류가 발생했습니다.");
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
	public List<ListDTO> pagination(Model model, @RequestBody Map<String, String> map, HttpServletRequest request) {
		/* 현재 필터링 되고 있는 서브메뉴 */
		if(map.get("type_cd") != null) {
			map.put("type", map.get("type_cd"));
		}

		/* 기본 정보 불러옴 */
		SessionUtil sessionUtil = new SessionUtil();
		sessionUtil.getSession(model, request, approvalService);
		
		if("sign".equals(map.get("path"))) {
			/* 진행중 문서 */
			map.put("status_cd", "'002'");
			map.put("code", model.getAttribute("coopCd").toString());
			map.put("title", "결재");
		} else if("signDoc".equals(map.get("path"))) {
			map.put("status_cd", "'002','003','999','005', '004'");
			map.put("pid", model.getAttribute("empid").toString());
			map.put("title", "결재문서");
		} else if("announcementCheck".equals(map.get("path"))){
			map.put("status_cd", "'002', '999', '003', '004'");
			map.put("code",model.getAttribute("posCd").toString());
			map.put("title", "공람");
		} else if("announcementDoc".equals(map.get("path"))) {
			map.put("status_cd", "'002','003','999','005','004'");
			map.put("title", "공람문서");
			map.put("pid", model.getAttribute("empid").toString());
		} else if("personalDoc".equals(map.get("path"))) {
			/* 현재 필터링 되고 있는 서브메뉴 */
			if(map.get("status_cd") != null) {
				String type = map.get("status_cd");
				map.put("status", type);
			}
		} else if("prograssDoc".equals(map.get("path"))) {
			map.put("status_cd", "'002'");
			map.put("code", model.getAttribute("coopCd").toString());
			map.put("title", "진행");
		}

		List<ListDTO> listDTOList = approvalService.list(map);
		
		return listDTOList;
	}

	/* edit 페이지 내 파일 삭제 */
	@PostMapping("/deleteFile")
	@ResponseBody
	public Map<String, String> deleteFile(@RequestParam("fileName") String fileName, @RequestParam(name = "attachid" ,required = false) Long attachid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int lastIndex = fileName.lastIndexOf("/");

        // '/'가 없는 경우 전체 파일 이름을 사용
        String pathSub = (lastIndex != -1) ? fileName.substring(lastIndex + 1) : fileName;

		Map<String, String> response = new HashMap<String, String>();

		String server = "ftp.jsoftone.co.kr";
        int port = 21;
        String user = "upload";
        String pass = "garam@019";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            boolean loggedIn = ftpClient.login(user, pass);
            if (!loggedIn) {
                response.put("msg", "FTP 서버에 로그인할 수 없습니다.");
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // FTP 서버에서 삭제할 파일 경로 설정
            String remoteFilePath = "/approval/" + session.getAttribute("custid") + "/" + pathSub;

			boolean deleted = ftpClient.deleteFile(remoteFilePath);
            if (!deleted) {
                response.put("msg", "파일을 삭제하지 못했습니다.");
            } else {
				if(attachid != null){
					approvalService.delOneAttach(attachid);
				}
				response.put("status", "success");
				response.put("msg", "파일을 삭제했습니다.");
			}

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

		return response;
	}
	
}
