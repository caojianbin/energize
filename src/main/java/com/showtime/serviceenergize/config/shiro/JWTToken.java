package com.showtime.serviceenergize.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName : JWTToken
 * @Description :JWTToken实体类
 * @Author : jlp
 * @Date: 2019-12-11 19:23
 */
public class JWTToken implements AuthenticationToken {
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
