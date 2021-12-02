package com.showtime.serviceenergize.config.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.showtime.serviceenergize.utils.JWTUtil;
import com.showtime.serviceenergize.utils.JedisUtil;
import com.showtime.serviceenergize.utils.UserConst;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName : CustomJWTFilter
 * @Description : 自定义JWTFilter
 * @Author : jlp
 * @Date: 2019-12-12 15:03
 */
@Component
public class CustomJWTFilter {

    // Token在Redis刷新时间,单位秒
    private static String refreshTime;

    // 批量提交数据Token在Redis刷新时间,单位秒
    private static String batchExpireTime;

    @Value("${refreshTime}")
    public void setRefreshTime(String refreshTime) {
        CustomJWTFilter.refreshTime = refreshTime;
    }

    @Value("${batchExpireTime}")
    public void setBatchExpireTime(String batchExpireTime) {
        CustomJWTFilter.batchExpireTime = batchExpireTime;
    }

    /**
     *@description 更新token信息：若token未过期则直接通过；若过期，则判断是否还有效（Redis时间），有效则刷新Token通过；
     * 若token既过期，也失效，判断是否批量提交数据（临时Redis时间内），若是批量提交的数据就通过，否则返回失效的旧token。
     *@Param
     *@return token
     *@Author jlp
     *@Date 2019/12/17 20:52
     *@throws
     */
    public String refreshToken(ServletRequest request, ServletResponse response){
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);

        // Token存入Redis的key
        String redisKey = UserConst.PREFIX_SHIRO_REFRESH_TOKEN;
        // 批量提交数据Token存入Redis的key
        String tempRedisKey = UserConst.PREFIX_SHIRO_PRE_REFRESH_TOKEN;

        String token = httpServletRequest.getHeader(UserConst.AUTHORIZATION);
        String userPhone = JWTUtil.getUsername(token);

        //判断Token是否过期
        try {
            JWTUtil.verify(token, userPhone);
        } catch (Exception e) {
            if(e instanceof TokenExpiredException) {
                //Token存入Redis的key
                redisKey = redisKey + userPhone;

                // 批量提交数据Token存入Redis的key
                tempRedisKey = tempRedisKey + userPhone;

                //判断是否失效
                Object redisTokenObj = JedisUtil.getObject(redisKey);

                //未失效（在一段时间内有使用）
                if(redisTokenObj != null){
                    String redisToken = (String) redisTokenObj;

                    //判断客户端传入的Token是否与redis中Token一致（避免用户篡改Token）
                    if(token.equals(redisToken)){
                        String newToken = JWTUtil.createToken(userPhone); //生成新Token

                        //新Token存放至Redis（作为时效时长）
                        JedisUtil.setObject(redisKey,newToken,Integer.valueOf(refreshTime));

                        //临时Token存放至Redis（作为批量提交缓存）
                        JedisUtil.setObject(tempRedisKey,token,Integer.valueOf(batchExpireTime));

                        return newToken;
                    }else {
                        //之前单位时间（较短时间）的旧token（处理同时提交的一批数据的多个请求问题）
                        Object oldRedisTokenObj = JedisUtil.getObject(tempRedisKey);
                        //当前token与同一批请求之前的token相同
                        if(oldRedisTokenObj != null && token.equals(oldRedisTokenObj)){
                            Object newTokenObj = JedisUtil.getObject(redisKey);
                            return (String) newTokenObj;
                        }
                    }
                }
            }
        }
        return token;

    }

    /**
     *@description  登录时将token存入Redis
     *@Param
     *@return
     *@Author jlp
     *@Date 2019/12/17 20:58
     *@throws
     */
    public String loginCreateToken(String userPhone, ServletResponse response){

        // Token存入Redis的key
        String redisKey = UserConst.PREFIX_SHIRO_REFRESH_TOKEN;

        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

        String token = JWTUtil.createToken(userPhone); //生成Token

        //Token存入Redis的key
        redisKey = redisKey + userPhone;

        //新Token存放至Redis
        JedisUtil.setObject(redisKey,token,Integer.valueOf(refreshTime));

        //Token存放至Header
        httpServletResponse.setHeader(UserConst.AUTHORIZATION, token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", UserConst.AUTHORIZATION);
        return token;
    }

}
