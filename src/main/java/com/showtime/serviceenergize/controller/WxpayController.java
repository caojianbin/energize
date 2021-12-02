package com.showtime.serviceenergize.controller;

import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.dto.PayDto;
import com.showtime.serviceenergize.service.WxPayService;
import com.showtime.serviceenergize.utils.WxpayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author cjb
 * @Date 2020/1/8 0008
 * @Description //TODO  微信支付处理
 */
//@Api(tags = "书唐的微信支付接口")
@RestController
//@RequestMapping("energize/wx/pay")
public class WxpayController {

    @Autowired
    private WxPayService wxPayService;

    /**
     * 支付回调处理
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
//    @RequestMapping(value = "/wxPayNotify")
    public String getPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //支付判断及处理
        return wxPayService.getPayNotify(request, response);
    }

    /**
     * 获取数据流数据
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static String receiveParam(HttpServletRequest request) throws IOException {
        StringBuffer receiveParam = new StringBuffer();
        InputStream inputStream = request.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader in = new BufferedReader(inputStreamReader);
        while (in.readLine() != null) {
            receiveParam.append(in.readLine());
        }
        in.close();
        inputStream.close();
        System.out.println("微信支付回调参数==" + receiveParam);
        return receiveParam.toString();
    }

    /**
     * 微信支付统一下单接口
     *
     * @param request
     * @return
     */
//    @ApiOperation(value = "微信创建订单", notes = "微信创建订单")
//    @RequestMapping(value = "/front/wxPay", method = RequestMethod.POST)
    public ResponseJsonCode wxPay(PayDto pay, HttpServletRequest request) {
        return wxPayService.wxPay(pay, request);
    }

    /**
     * 微信订单查询
     *
     * @return
     */
    public ResponseJsonCode wechatOrderInquiry() {
        return null;
    }

}
