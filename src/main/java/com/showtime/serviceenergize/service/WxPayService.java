package com.showtime.serviceenergize.service;

import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.dto.PayDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author cjb
 *
 * @Date 2020/1/8 0008
 *
 * @Description //TODO 微信支付 service
 */
public interface WxPayService {

    /**
     * 支付回调处理
     * @description : //TODO 支付回调处理
     * @author :   zs
     * @param request
     * @param response
     * @return      ResponseJsonCode
     * @exception
     * @date :       2019/5/5 9:56
     */
    String getPayNotify(HttpServletRequest request, HttpServletResponse response);

    /**
     * 微信支付统一下单接口
     *
     * @param request
     * @return
     */
    ResponseJsonCode wxPay(PayDto pay, HttpServletRequest request);
    

}
