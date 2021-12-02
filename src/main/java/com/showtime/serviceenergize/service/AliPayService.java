package com.showtime.serviceenergize.service;

import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.dto.PayDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author cjb
 * @Date 2020/1/2 15:15
 * @Description //TODO 支付宝支付的service
 */
public interface AliPayService {
    /**
     * 创建订单
     *
     * @param schId  学校ID
     * @param uId    用户id
     * @param option 日 周 月
     * @return
     * @throws Exception
     */
    ResponseJsonCode create(PayDto pay) throws Exception;

    /**
     * 异步通知
     *
     * @param response
     * @param request
     * @throws Exception
     */
    String notify(HttpServletResponse response, HttpServletRequest request) throws Exception;
}
