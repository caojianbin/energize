package com.showtime.serviceenergize.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.util.StringUtil;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.StEnergizeCommercial;
import com.showtime.serviceenergize.service.StEnergizeCommercialService;
import com.showtime.serviceenergize.utils.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author cjb
 * @Date 2020/4/2 0002
 * @Description //TODO
 */
public class EnergizeInterceptor implements HandlerInterceptor {
    /**
     * 商户管理service
     */
    @Autowired
    private StEnergizeCommercialService commercialService;

    /**
     * 请求拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //从header中拿到token(商户)
        String token = request.getHeader("token");
        if (null == token) {
            responseError(response, 500, "token为空！");
            return false;
        }

        //验证token是否是sql注入
        if (!StringUtils.isSqlValid(token)) {
            responseError(response, 500, "token非法！");
            return false;
        }
        //根据token查询商户是否存在是否过期
        StEnergizeCommercial commercial = commercialService.getOne(new QueryWrapper<StEnergizeCommercial>().eq("ct_identification_code", token));
        if (null == commercial) {
            responseError(response, 500, "商户不存在！");
            return false;
        } else {
            Integer ctStatus = commercial.getCtStatus();
            if (ctStatus != null) {
                if (ctStatus == 2) {
                    responseError(response, 500, "与商户已停止合作！");
                    return false;
                }

            }
            //商户存在的情况下判断合作是否过期
            String ctEndTime = commercial.getCtEndTime();
            if (StringUtil.isEmpty(ctEndTime)) {
                responseError(response, 500, "商户还未合作！");
                return false;
            }
            //判断商户是否合作到期
            long currentTime = System.currentTimeMillis();
            //没过期直接允许访问
            if (currentTime < Long.valueOf(ctEndTime)) {
                return true;
            } else {
                responseError(response, 500, "商户合同已过期！");
                return false;
            }
        }
    }

    /**
     * 将非法请求跳转到 /unauthorized/**
     */
    private void responseError(ServletResponse response, Integer code, String message) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            String data = JSONObject.toJSONString(ResponseJsonCode.cusResponseJsonCode(code, message));
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }
}
