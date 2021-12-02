package com.showtime.serviceenergize.utils;


import com.showtime.common.exception.MyException;
import com.showtime.common.model.ResultEnum;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * AES加解密工具类
 * </p>
 *
 * @author cjb
 * @since 18:11
 */
public class AESUtil {
    //秘钥
    private static String KEY =  "CjbSWzddG/*-`+!@";

    public static String encrypt(String sSrc, String sKey) {
        if (sKey == null) {
            throw new IllegalArgumentException("sSrc不能为空");
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            throw new IllegalArgumentException("sKey长度需要为16位");
        }

        try {
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));

            //此处使用BASE64做转码功能，同时能起到2次加密的作用。
            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String sSrc, String sKey) {

        if (sKey == null) {
            throw new IllegalArgumentException("sSrc不能为空");
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            throw new IllegalArgumentException("sKey长度需要为16位");
        }

        try {
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //先用base64解密
            byte[] encrypted1 = new Base64().decode(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, StandardCharsets.UTF_8);
            return originalString;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

//    public static void main(String[] args) {
//        String decrypt = AESUtil.encrypt("15982351826", KEY);
//        System.out.println(decrypt);
//    }

    /**
     * 输入密码进行解密
     *
     * @param password 密码
     * @return
     */
    public static String getPassword(String password){
        if (null == password){
            throw new MyException(ResultEnum.OBJECT_IS_NULL);
        }
        String decrypt = AESUtil.decrypt(password, KEY);
        return decrypt;
    }

    /**
     * 输入原文进行加密
     *
     * @param password 原文
     * @return
     */
    public static String setPassword(String password){
        if (null == password){
            throw new MyException(ResultEnum.OBJECT_IS_NULL);
        }
        String encrypt = AESUtil.encrypt(password, KEY);
        return encrypt;
    }
}