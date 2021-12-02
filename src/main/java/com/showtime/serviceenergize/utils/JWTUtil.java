package com.showtime.serviceenergize.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.showtime.serviceenergize.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @ClassName : JWTUtil
 * @Description :JWT工具栏
 * @Author : jlp
 * @Date: 2019-12-11 19:20
 */
@Component
public class JWTUtil {

    // 过期时间
    private static String expireTime ;

    @Value("${expireTime}")
    public void setExpireTime(String expireTime) {
        JWTUtil.expireTime = expireTime;
    }

    public static String getExpireTime() {
        return expireTime;
    }

    // 密钥
    private static final String SECRET = "SHIRO+JWT";


    /**
     * 生成 token
     * @param username 用户名
     * @return 加密的token
     */
    public static String createToken(String username) {
        try {
            Date date = new Date(System.currentTimeMillis() + Long.valueOf(expireTime) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 附带username信息
            return JWT.create().withClaim(UserConst.USER_PHONE, username)
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 校验 token 是否正确
     * @param token    密钥
     * @param username 用户名
     * @return 是否正确
     */
    public static boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm).withClaim(UserConst.USER_PHONE, username).build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            if(exception instanceof InvalidClaimException){
                throw new InvalidClaimException("Token无效");
            }else if(exception instanceof TokenExpiredException){
                throw new TokenExpiredException("Token过期");
            }else if(exception instanceof UnsupportedEncodingException){
                throw new CustomException("JWTToken认证解密出现UnsupportedEncodingException异常:" + exception.getMessage());
            }
            return false;
        }
    }

    public static void main(String[] args) {
        String tkoen = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3Bob25lIjoiMTU5ODIzNTE4MjYiLCJleHAiOjE1NzgwMjMzOTl9.gR61khFQEZjejXDEpGIau0m9vFrjzMNqy0ya-EuAo0E";
        System.out.println(JWTUtil.verify(tkoen, "user_phone"));
    }

    /**
     *@description  获得token中的信息，含的用户名
     *@Param token token串
     *@return 用户名
     *@Author jlp
     *@Date 2019/12/11 16:40
     *@throws
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(UserConst.USER_PHONE).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
