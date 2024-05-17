package com.jsone.approval.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CommonUtil {
    public String removeTagString(String strParam) {
        // Jsoup을 사용하여 HTML을 파싱합니다.
        Document doc = Jsoup.parse(strParam);
        
        // 처리된 HTML을 문자열로 반환합니다.
        return doc.text();
    }
}
