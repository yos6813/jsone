package com.jsone.approval.util;

public class CommonUtil {
    public String removeTagString(String strParam) {
        String strReturn = "";
        String strRegExp = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";

        if(strParam != null) {
            strReturn = strParam.replaceAll(strRegExp, "");
        }
        return strReturn;
    }
}
