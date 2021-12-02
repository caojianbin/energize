package com.showtime.serviceenergize.utils;

import java.security.MessageDigest;

/**
 * @Author zs
 * @Date 2019/4/25 15:35
 * @Description //TODO ODO MD5加密  编码:UTF-8
 */

public class MD5Util {


    public static String md5Encode(String paramStr) {
        String encodeStr = null;
        try {
            encodeStr = new String(paramStr);
            MessageDigest md = MessageDigest.getInstance("MD5");
            encodeStr = byteArrayToHexString(md.digest(encodeStr.getBytes("UTF-8")));
        } catch (Exception exception) {
        }
        return encodeStr;
    }


    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    private static final String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}

