package com.jsone.approval.util;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SmsUtil {
    private final RestTemplate restTemplate;

    public SmsUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> sendSms(Map<String, String> sms) {
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Multipart 요청 본문 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : sms.entrySet()) {
            body.add(entry.getKey(), entry.getValue());
        }

        // 요청 엔터티 생성
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // SMS API 엔드포인트
        String smsUrl = "https://apis.aligo.in/send/";

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(smsUrl, requestEntity, String.class);
        
        return response;
    }

    public ResponseEntity<String> sendKakao(Map<String, String> kakao) {
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Multipart 요청 본문 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : kakao.entrySet()) {
            body.add(entry.getKey(), entry.getValue());
        }

        // 요청 엔터티 생성
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Kakao API 엔드포인트
        String kakaoUrl = "https://kakaoapi.aligo.in/akv10/alimtalk/send/";

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(kakaoUrl, requestEntity, String.class);
        
        return response;
    }

    /* 전송결과 상세보기 */
    public ResponseEntity<String> resultKakao(Map<String, String> kakao) {
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Multipart 요청 본문 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : kakao.entrySet()) {
            body.add(entry.getKey(), entry.getValue());
        }

        // 요청 엔터티 생성
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String resultUrl = "https://kakaoapi.aligo.in/akv10/history/detail/";

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(resultUrl, requestEntity, String.class);
        
        return response;
    }
}
