package com.showtime.serviceenergize.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author cjb
 *
 * @Date 2020/1/8
 *
 * @Description //TODO 微信支付配置类
 */
@Component
@Data
public class WechatConfig implements Serializable {
    //小程序appid  开发者自己拥有的
    @Value("${applet.appid}")
    public String appid;

    //微信支付的商户id   开发者去问自己id的前端同事或者领导。
    @Value("${applet.mch_id}")
    public String mch_id;

    //微信支付的商户密钥  开发者问领导，去微信商户平台去申请（要下插件等等）
    @Value("${applet.key}")
    public String key;

    //支付成功后的服务器回调url，这里填Controller里的回调函数地址
    @Value("${applet.notify_url}")
    public String notify_url;

    //签名方式，固定值
    public static final String SIGNTYPE = "MD5";

    //交易类型，微信H5支付的固定值为MWEB
    public static final String TRADETYPE = "MWEB";

    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //微信查看订单地址
    public static final String order_query_url = "https://api.mch.weixin.qq.com/pay/orderquery";

    //关闭订单地址
    public static final String close_order_url = "https://api.mch.weixin.qq.com/pay/closeorder";

    public static final String company_website = "http://nstygf.natappfree.cc";
}