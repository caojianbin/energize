package com.showtime.serviceenergize.utils;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @Author zs
 * @Date 2019/4/30 9:01
 * @Description //TODO xml 解析工具
 */

public class XMLUtil {
    /**
     * <p>方法说明: TODO 解析XML,返回第一级元素键值对;如果第一级元素有子节点，则此节点的值是子节点的XML数据;
     * <p>参数说明: String xmlStr XML格式字符串
     * <p>参数说明: @throws JDOMException
     * <p>参数说明: @throws IOException
     * <p>返回说明: Map<String,String> xmlMap 解析后的数据
     * <p>创建时间: 2017年11月2日 上午10:04:39
     * <p>创  建  人: zs
     **/
    public static Map<String, Object> doXMLParse(String xmlStr) throws JDOMException, IOException {
        xmlStr = xmlStr.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (StringUtils.isEmpty(xmlStr)) {
            return null;
        }
        Map<String, Object> xmlMap = new HashMap<>();
        InputStream in = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List<Element> list = root.getChildren();
        Iterator<Element> it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            Object v = "";
            List<Element> children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = XMLUtil.getXMLStr(children);
            }
            xmlMap.put(k, v);
        }
        in.close(); // 关闭流
        return xmlMap;
    }

    /**
     * <p>方法说明: TODO 获取子结点的XML,生成XML格式字符串
     * <p>参数说明: List<Element> listElement
     * <p>返回说明: XML格式字符串
     * <p>创建时间: 2017年11月2日 上午9:58:59
     * <p>创  建  人: geYang
     **/
    private static String getXMLStr(List<Element> listElement) {
        StringBuffer xmlStr = new StringBuffer();
        if (!listElement.isEmpty()) {
            Iterator<Element> it = listElement.iterator();
            while (it.hasNext()) {
                Element e = it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List<Element> listChildren = e.getChildren();
                xmlStr.append("<" + name + ">");
                if (!listChildren.isEmpty()) {
                    xmlStr.append(XMLUtil.getXMLStr(listChildren)); // 递归
                }
                xmlStr.append(value);
                xmlStr.append("</" + name + ">");
            }
        }
        return xmlStr.toString();
    }

    /**
     *<p>方法说明: TODO 获取XML编码字符集
     *<p>参数说明: String xmlStr
     *<p>参数说明: @return
     *<p>参数说明: @throws JDOMException
     *<p>参数说明: @throws IOException
     *<p>返回说明:
     *<p>创建时间: 2017年11月2日 上午10:31:16
     *<p>创  建  人: geYang
     **/
    public static String getXMLEncoding(String xmlStr) throws JDOMException, IOException {
        InputStream in = new ByteArrayInputStream(xmlStr.getBytes());
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(in);
        in.close();
        return (String)document.getProperty("encoding");
    }


    /**
     *<p>方法说明: TODO 支付成功(失败)后返回给微信的参数
     *<p>参数说明: String return_code
     *<p>参数说明: String return_msg
     *<p>返回说明:
     *<p>创建时间: 2017年11月2日 上午10:42:10
     *<p>创  建  人: geYang
     **/
    public static String setXML(String returnCode, String returnMsg) {
        return "<xml><return_code><![CDATA[" + returnCode
                + "]]></return_code><return_msg><![CDATA[" + returnMsg
                + "]]></return_msg></xml>";
    }






    public static void main(String[] args) {
        StringBuffer xmlStr = new StringBuffer();
        xmlStr.append("<xml>");
        xmlStr.append("<appid>wx2421b1c4370ec43b</appid>");
        xmlStr.append("<attach>支付测试</attach>");
        xmlStr.append("<body>APP支付测试</body>");
        xmlStr.append("<mch_id>10000100</mch_id>");
        xmlStr.append("<nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>");
        xmlStr.append("<notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>");
        xmlStr.append("<out_trade_no>1415659990</out_trade_no>");
        xmlStr.append("<spbill_create_ip>14.23.150.211</spbill_create_ip>");
        xmlStr.append("<total_fee>1</total_fee>");
        xmlStr.append("<trade_type>APP</trade_type>");
        xmlStr.append("<sign>0CB01533B8C1EF103065174F50BCA001</sign>");
        xmlStr.append("</xml>");
        System.out.println("解析前=="+xmlStr);
        Map<String, Object> doXMLParse = new HashMap<>();
        try {
            doXMLParse = XMLUtil.doXMLParse(xmlStr.toString());
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
            System.out.println("解析Map失败");
        }
        System.out.println("解析后=="+doXMLParse);

    }


}
