package com.showtime.serviceenergize.utils;


import com.showtime.serviceenergize.config.WechatConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;


/**
 * @Author zs
 * @Date 2019/4/30 9:03
 * @Description //TODO 微信支付工具
 */
@Component
public class WxpayUtil {
    private static final String STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Autowired
    private WechatConfig wechatConfig;
    /**
     *<p>方法说明: TODO 获取微信回调地址
     *<p>创建时间: 2019年4月30日 下午1:21:05
     *<p>创  建  人: zs
     **/
    public  String getNotifyUrl(HttpServletRequest request){
        StringBuffer requestURL = request.getRequestURL();
        return requestURL.substring(0,requestURL.indexOf("/front/"))+ wechatConfig.getNotify_url();
    }
    /**
     *<p>方法说明: TODO 统一下单 参数处理
     *<p>参数说明: String orderNo       订单号
     *<p>参数说明: String totalAmount   订单总金额
     *<p>参数说明: String notifyUrl     回调通知地址
     *<p>参数说明: String body          订单描述
     *<p>返回说明:
     *<p>创建时间: 2019年4月30日 上午9:56:23
     *<p>创  建  人: zs
     **/
    public  String getUnifiedOrder(String orderNo,String totalAmount,String notifyUrl,String body){
        SortedMap<String,Object> paramMap = new TreeMap<>();
        //应用ID
        paramMap.put("appid", wechatConfig.getAppid());
        //商户号
        paramMap.put("mch_id",wechatConfig.getMch_id());
        //随机字符串
        paramMap.put("nonce_str", getNonceStr(null));
        //商品描述
        paramMap.put("body", body);
        //商户订单号
        paramMap.put("out_trade_no", orderNo);
        //支付总金额
        paramMap.put("total_fee", Integer.parseInt(getTotalFee(totalAmount)));
        //当前本机IP
        paramMap.put("spbill_create_ip",getLocalhostIP());
        //回调通知地址
        paramMap.put("notify_url", notifyUrl);
        //支付类型
        paramMap.put("trade_type", wechatConfig.TRADETYPE);
        //场景信息
        paramMap.put("scene_info", "{\"h5_info\":{\"type\":\"Wap\",\"wap_url\":"+wechatConfig.company_website+",\"wap_name\": \"可视化校园支付\"}}");
        //数据签名
        paramMap.put("sign", rsaSign(paramMap));
        //请求参数
        return getRequestXML(paramMap);
    }
    /**
     *<p>方法说明: TODO 返回APP 参数处理
     *<p>参数说明: String prepayId   预支付交易会话ID
     *<p>返回说明:
     *<p>创建时间: 2019年4月30日 上午10:00:30
     *<p>创  建  人: zs
     **/
    public String getReturnParam(Map<String, Object> receiveMap){
        String timestamp = (System.currentTimeMillis()+"").substring(0, 10);
        SortedMap<String,Object> returnMap = new TreeMap<>();
        //小程序ID
        returnMap.put("appId" , wechatConfig.getAppid());
        //时间戳
        returnMap.put("timeStamp", timestamp);
        //prepayId
        returnMap.put("package", "prepay_id="+receiveMap.get("prepay_id"));
        //signType
        returnMap.put("signType", wechatConfig.SIGNTYPE);
        //随机字符串
        returnMap.put("nonceStr", receiveMap.get("nonce_str"));
        //签名
        returnMap.put("paySign", rsaSign(returnMap));
        //请求参数
        return getRequestXML(returnMap);
}
    /**
     *<p>方法说明: TODO 关闭/查看 订单 参数处理
     *<p>参数说明: String orderNo 商户订单号
     *<p>创建时间: 2019年4月30日 下午2:55:53
     *<p>创  建  人: zs
     **/
    public  String closeORqueryOrderParam(String orderNo) {
        SortedMap<String,Object> returnMap = new TreeMap<>();
        returnMap.put("appid", wechatConfig.getAppid() );      //小程序ID
        returnMap.put("mch_id",wechatConfig.getMch_id() );    //商户号
        returnMap.put("out_trade_no", orderNo);         //商户订单号
        returnMap.put("nonce_str", getNonceStr(null));  //随机字符串
        returnMap.put("sign", rsaSign(returnMap));      //签名
        return getRequestXML(returnMap);     //请求参数
    }
    /**
     *<p>方法说明: TODO 验证签名
     *<p>参数说明: Map<String,String> paramMap
     *<p>返回说明: boolean
     *<p>创建时间: 2019年4月30日 上午11:18:47
     *<p>创  建  人: zs
     **/
    public  boolean rsaCheck(Map<String,Object> paramMap){
        System.out.println("↓↓↓↓↓↓↓↓↓↓微信支付签名验证处理↓↓↓↓↓↓↓↓↓↓");
        SortedMap<String,Object> paramSorMap = new TreeMap<>();
        paramSorMap.putAll(paramMap);
        boolean rsaCheck = paramMap.get("sign").equals(rsaSign(paramSorMap));
        System.out.println("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
        return rsaCheck;
    }
    /**
     *<p>方法说明: TODO sign签名
     *<p>参数说明: SortedMap<String,String> paramMap
     *<p>返回说明: String
     *<p>创建时间: 2019年4月30日 下午3:45:02
     *<p>创  建  人: zs
     **/
    private  String rsaSign(SortedMap<String,Object> paramMap){
        StringBuffer paramStr = new StringBuffer();
        Set<Entry<String, Object>> paramSet = paramMap.entrySet();
        Iterator<Entry<String, Object>> it = paramSet.iterator();
        while(it.hasNext()) {
            Entry<String, Object> entry = it.next();
            String k = entry.getKey();
            String v = String.valueOf(entry.getValue());
            if(!StringUtils.isEmpty(v) && !"sign".equals(k) && !"key".equals(k)) {
                paramStr.append(k + "=" + v + "&");
            }
        }
        //注：key为商户平台设置的密钥key
        paramStr.append("key="+wechatConfig.getKey() );
        //注：MD5签名方式
        String sign = MD5Util.md5Encode(paramStr.toString()).toUpperCase();
        System.out.println("微信签名参数=="+paramStr.toString());
        System.out.println("微信签  名  值=="+sign);
        return sign;
    }
    /**
     *<p>方法说明: TODO 生成随机字符串
     *<p>参数说明: Integer length 默认16
     *<p>返回说明: 长度为 length 的字符串
     *<p>创建时间: 2019年4月30日 下午2:57:57
     *<p>创  建  人: zs
     **/
    private  String getNonceStr(Integer length) {
        length = 32;
        StringBuffer nonceStr = new StringBuffer();
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            nonceStr.append(STR.charAt(rd.nextInt(STR.length() - 1)));
        }
        return nonceStr.toString();
    }
    /**
     *<p>方法说明: TODO 获取本机IP
     *<p>返回说明: 通过 获取系统所有的networkInterface网络接口 然后遍历 每个网络下的InterfaceAddress组,
     *          获得符合  "InetAddress instanceof Inet4Address" 条件的一个IpV4地址
     *<p>创建时间: 2019年4月30日 下午2:02:20
     *<p>创  建  人: zs
     **/
    public   String getLocalhostIP(){
        String ip = null;
        Enumeration<?> allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> interfaceAddress = netInterface.getInterfaceAddresses();
                for (InterfaceAddress add : interfaceAddress) {
                    InetAddress inetAddress = add.getAddress();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) {
                        ip = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("获取IP异常");
        }
        return ip;
    }
    /**
     *<p>方法说明: TODO 将请求参数转换为XML格式的String
     *<p>参数说明: SortedMap<String,String> paramMap
     *<p>返回说明: String
     *<p>创建时间: 2019年4月30日 下午4:16:21
     *<p>创  建  人: zs
     **/
    private  String getRequestXML(SortedMap<String,Object> paramMap){
        StringBuffer requestXML = new StringBuffer();
        requestXML.append("<xml>");
        Set<Entry<String, Object>> paramSet = paramMap.entrySet();
        Iterator<Entry<String, Object>> it = paramSet.iterator();
        while(it.hasNext()) {
            Entry<String, Object> entry = it.next();
            String k = entry.getKey();
//            String v = String.valueOf(entry.getValue());
            requestXML.append("<"+k+">"+entry.getValue()+"</"+k+">");
        }
        requestXML.append("</xml>");
        System.out.println("微信请求参数=="+requestXML);
        return requestXML.toString();
    }
    /**
     *<p>方法说明: TODO 获取整数格式支付总金额
     *<p>参数说明: String totalAmount  保留两位小数的金额
     *<p>返回说明: String getTotalFee  整数的金额
     *<p>创建时间: 2019年4月30日下午5:39:03
     *<p>创  建  人: zs
     **/
    private  String getTotalFee(String totalAmount){
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        return decimalFormat.format(Double.valueOf(totalAmount)*100);
    }


    /**
     *<p>方法说明: TODO 订单查看处理
     *<p>创建时间: 2019年4月30日 下午3:34:16
     *<p>创  建  人: zs
     **/
    public  Map<String,Object> orderQuery(String orderNo) throws Exception{
        System.out.println("↓↓↓↓↓↓↓↓↓↓微信支付订单查看处理↓↓↓↓↓↓↓↓↓↓");
        String receiveParam = HttpUtil.doPostToStr(WechatConfig.order_query_url, closeORqueryOrderParam(orderNo));
        return XMLUtil.doXMLParse(receiveParam);
    }
    /**
     *<p>方法说明: TODO 订单关闭处理
     *<p>创建时间: 2019年4月30日 下午3:34:16
     *<p>创  建  人: zs
     **/
    public  Map<String,Object> colseOrder(String orderNo) throws Exception{
        System.out.println("↓↓↓↓↓↓↓↓↓↓微信支付订单关闭处理↓↓↓↓↓↓↓↓↓↓");
        String receiveParam = HttpUtil.doPostToStr(WechatConfig.close_order_url, closeORqueryOrderParam(orderNo));
        return XMLUtil.doXMLParse(receiveParam);
    }



}
