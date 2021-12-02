package com.showtime.serviceenergize.controller;

import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.dto.PayDto;
import com.showtime.serviceenergize.service.AliPayService;
import com.showtime.serviceenergize.utils.JedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author cjb
 * @Date 2020/1/2 15:15
 * @Description //TODO 支付宝支付的控制类
 */
//@Api(tags = "(书唐赋能)-支付宝支付接口（*有修改2020-1.14）")
@RestController
@Slf4j
//@RequestMapping("/ali/pay")
public class AlipayController {
    /**
     * 支付宝支付的service
     */
    @Autowired
    private AliPayService payService;

    /**
     * 快捷支付调用支付宝支付接口
     *
     * @return
     * @throws Exception
     */
//    @ApiOperation(value = "(赋能手机H5支付接口)-支付宝创建订单", notes = "赋能手机H5支付接口)-支付宝创建订单")
//    @PostMapping("/create")
    public ResponseJsonCode create(@RequestBody PayDto pay,HttpServletRequest request)
            throws Exception {
        return  payService.create(pay);
    }
    /**
     * p2p后台返回的操作
     *
     * @param response
     * @param request
     * @throws Exception
     */
//    @RequestMapping("/notify_url")
    public String notify(HttpServletResponse response, HttpServletRequest request) throws Exception {
        return payService.notify(response,request);
    }

    /**
     * 同步通知的页面的Controller
     *
     * @return
     * @throws InterruptedException
     */
//    @RequestMapping("/return_url")
    public void Return_url(HttpServletResponse response,HttpServletRequest request) throws InterruptedException {
        try {
            PayDto alipay = (PayDto)JedisUtil.getObject("alipay");
            if (alipay != null){
                StringBuffer url = new StringBuffer("https://view.energy.showtimer.cn/#/");
                if (!"details".equals(alipay.getPoint())){
                    url.append("activation");
                }
                response.sendRedirect(url +
                        "?point="+alipay.getPoint()+"&" +
                        "ctIdentificationCode="+alipay.getCtIdentificationCode()+"&" +
                        "userNumber="+alipay.getUserNumber()+"&" +
                        "schAdmissionCode="+alipay.getSchAdmissionCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
