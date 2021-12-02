package com.showtime.serviceenergize.utils.common;

import com.showtime.serviceenergize.utils.JWTUtil;
import com.showtime.serviceenergize.utils.UserConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * TokenUtil
 * </p>
 *
 * @author cjb
 * @since 11:11
 */
@Component
public class TokenUtil {

    public static String getUserPhone(HttpServletRequest request){
        //先从token中拿用户，如果没有再从请求中拿用户
        String token = request.getHeader(UserConst.AUTHORIZATION);
        //调用jwtutil拿到用户信息
        String username = JWTUtil.getUsername(token);
        return username;
    }
}
