package com.showtime.serviceenergize.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author cjb
 * @Date 2020/1/2 15:15
 * @Description //TODO 支付宝支付的一些配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "ali")
public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    private String app_id;//例：2016082600317257

    // 商户私钥，您的PKCS8格式RSA2私钥
    private String merchant_private_key;
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
    // 对应APPID下的支付宝公钥。
    private String alipay_public_key;
    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    /**
     * 返回的时候此页面不会返回到用户页面，只会执行你写到控制器里的地址
     */
    private String notify_url;
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    /**
     * 此页面是同步返回用户页面，也就是用户支付后看到的页面，上面的notify_url是异步返回商家操作，谢谢
     * 要是看不懂就找度娘，或者多读几遍，或者去看支付宝第三方接口API，不看API直接拿去就用，遇坑不怪别人
     */
    private String return_url;
    // 签名方式
    private String sign_type;

    // 字符编码格式
    private String charset;
    // 支付宝网关
    private String gatewayUrl;

    private String format = "json";

}
