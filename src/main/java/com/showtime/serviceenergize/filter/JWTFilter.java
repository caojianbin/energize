package com.showtime.serviceenergize.filter;

import com.alibaba.fastjson.JSONObject;
import com.showtime.common.model.ResultEnum;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.config.shiro.CustomJWTFilter;
import com.showtime.serviceenergize.config.shiro.JWTToken;
import com.showtime.serviceenergize.exception.CustomException;
import com.showtime.serviceenergize.utils.UserConst;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName : JWTFilter
 * @Description :JWT过滤器
 * @Author : jlp
 * @Date: 2019-12-11 19:25
 */
public class JWTFilter extends BasicHttpAuthenticationFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());



    public JWTFilter(){
    }

    /**
     * 如果带有 token，则对 token 进行检查，否则直接通过
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        //判断请求的请求头是否带上 "Token"
        if (this.isLoginAttempt(request, response)) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                this.executeLogin(request , response);

//                JWTToken jwtToken = new JWTToken(newToken);
//                this.executeLogin(request , httpServletResponse);
//                this.getSubject(request, response).login(jwtToken);

                return true;
            } catch (Exception e) {
                //token 错误
                this.responseError(response, e.getMessage());
            }
        }
        //如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return false;
    }

    /**
     * 判断用户是否想要登入。
     * 检测 header 里面是否包含 Token 字段
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(UserConst.AUTHORIZATION);
        return token != null;
    }

    /**
     * 执行登陆操作
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String token = httpServletRequest.getHeader(UserConst.AUTHORIZATION);

        String newToken = new CustomJWTFilter().refreshToken(request, response);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        //Token存放至Header
        httpServletResponse.setHeader(UserConst.AUTHORIZATION, newToken);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", UserConst.AUTHORIZATION);

        JWTToken jwtToken = new JWTToken(newToken);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /unauthorized/**
     */
    private void responseError(ServletResponse response, String message) {

        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = httpServletResponse.getWriter()) {
            String data = JSONObject.toJSONString(ResponseJsonCode.cusResponseJsonCode(ResultEnum.AUTHENTICATION_FAILED.getCode() ,
                                                                                 ResultEnum.AUTHENTICATION_FAILED.getMsg() + message));
            out.append(data);
        } catch (IOException e) {
            logger.error("直接返回Response信息出现IOException异常:" + e.getMessage());
            throw new CustomException("直接返回Response信息出现IOException异常:" + e.getMessage());
        }
    }
}
