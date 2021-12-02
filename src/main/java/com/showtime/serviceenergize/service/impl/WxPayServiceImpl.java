package com.showtime.serviceenergize.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.showtime.common.exception.MyException;
import com.showtime.common.model.ResultEnum;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.common.utils.JudgeNumberUtil;
import com.showtime.serviceenergize.config.WechatConfig;
import com.showtime.serviceenergize.entity.StEnergizeOrder;
import com.showtime.serviceenergize.entity.StEnergizeUserUnlockSchool;
import com.showtime.serviceenergize.entity.dto.PayDto;
import com.showtime.serviceenergize.mapper.GetSchoolPriceSlaveMapper;
import com.showtime.serviceenergize.mapper.StEnergizeOrderMapper;
import com.showtime.serviceenergize.mapper.StEnergizeUserUnlockSchoolMapper;
import com.showtime.serviceenergize.mapper.StSchSchoolMapper;
import com.showtime.serviceenergize.service.WxPayService;
import com.showtime.serviceenergize.utils.*;
import com.showtime.serviceenergize.utils.common.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author zs
 * @Date 2019/5/5 9:32
 * @Description //TODO 微信支付service 实现类
 */
@Transactional(rollbackFor = Exception.class)
@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {


    @Autowired
    private WxpayUtil wxpayUtil;

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private GetSchoolPriceSlaveMapper slaveMapper;

    @Autowired
    private StEnergizeOrderMapper orderMapper;

    @Autowired
    private StSchSchoolMapper schSchoolMapper;

    @Autowired
    private StEnergizeUserUnlockSchoolMapper unlockSchoolMapper;

    @Override
    public String getPayNotify(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("↓↓↓↓↓↓↓↓↓↓微信支付回调业务处理↓↓↓↓↓↓↓↓↓↓");
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");
            System.out.println("微信支付通知结果：" + result);
            Map<String, Object> map = null;
            try {
                /**
                 * 解析微信通知返回的信息
                 */
                map = XMLUtil.doXMLParse(result);
            } catch (JDOMException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("=========:" + result);
            // 若支付成功，则告知微信服务器收到通知
            if (map.get("return_code").equals("SUCCESS")) {
                //签名验证
                if (!wxpayUtil.rsaCheck(map)) {
                    System.out.println("微信支付回调签名验证失败");
                    throw new MyException(ResultEnum.ST_PAY_ALI_SIGN_FAIL);
                }
                //验证成功，修改订单状态
                StEnergizeOrder energizeOrder = orderMapper.selectOne(new QueryWrapper<StEnergizeOrder>().eq("order_reference", String.valueOf(map.get("out_trade_no"))));
                if (null == energizeOrder) {
                    throw new MyException(ResultEnum.NO_ORDER_INFORMATION_FOUND);
                }
                //将用户解锁信息存入
                StEnergizeUserUnlockSchool unlockSchool = new StEnergizeUserUnlockSchool();

                //修改状态(已支付)
                energizeOrder.setOrderStatu(1);
                //插入支付金额
                energizeOrder.setPayMoney(new BigDecimal(Float.parseFloat(String.valueOf(map.get("cash_fee"))) / 100));
                //插入支付时间
                String timeEnd = (String) map.get("time_end");
                if (timeEnd == null){
                    throw new MyException(ResultEnum.NO_ORDER_INFORMATION_FOUND);
                }
                System.out.println("-----------------支付时间---------------：" + timeEnd);
                //转为时间戳
                long timesTamp = DateUtil.strDateTwo(timeEnd).getTime();
                String valueOfTime = String.valueOf(timesTamp);
                //购买开始时间
                energizeOrder.setOrderPayTime(valueOfTime);
                unlockSchool.setSeuStartTime(valueOfTime);
                //过期时间
                Calendar calendar = Calendar.getInstance();
                //插入到期时间
                Date unlockTime = DateUtil.strDateTwo(timeEnd);
                calendar.setTime(unlockTime);
                Date time = null;
                String toStr = null;
                if (1 == energizeOrder.getPreOption()) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    time = calendar.getTime();
                    toStr = DateUtil.dateToStr(time);
                    energizeOrder.setExpirationTime(DateUtil.timesTamp(toStr));
                    unlockSchool.setSeuEndTime(DateUtil.timesTamp(toStr));
                } else if (7 == energizeOrder.getPreOption()) {
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                    time = calendar.getTime();
                    toStr = DateUtil.dateToStr(time);
                    energizeOrder.setExpirationTime(DateUtil.timesTamp(toStr));
                    unlockSchool.setSeuEndTime(DateUtil.timesTamp(toStr));
                } else if (30 == energizeOrder.getPreOption()) {
                    calendar.add(Calendar.DAY_OF_MONTH, 30);
                    time = calendar.getTime();
                    toStr = DateUtil.dateToStr(time);
                    energizeOrder.setExpirationTime(DateUtil.timesTamp(toStr));
                    unlockSchool.setSeuEndTime(DateUtil.timesTamp(toStr));
                }

                //插入微信支付单号
                energizeOrder.setTranOrderNum(String.valueOf(map.get("transaction_id")));
                //保存订单
                int i = orderMapper.update(energizeOrder, new UpdateWrapper<StEnergizeOrder>().eq("order_reference", String.valueOf(map.get("out_trade_no"))));
                if (i == 0) {
                    throw new MyException(ResultEnum.ORDER_INSERTION_FAILED);
                }

                unlockSchool.setCtIdentificationCode(energizeOrder.getCtIdentificationCode());
                unlockSchool.setSchId(energizeOrder.getSchId());
                unlockSchool.setUserNumber(energizeOrder.getUserNumber());
                unlockSchool.setSeuId(UUIDUtil.getUuid());
                //将用户解锁信息存入\修改
                StEnergizeUserUnlockSchool unlockSchool1 = unlockSchoolMapper.selectOne(new QueryWrapper<StEnergizeUserUnlockSchool>().eq("ct_identification_code", energizeOrder.getCtIdentificationCode()).eq("user_number", energizeOrder.getUserNumber()).eq("sch_id", energizeOrder.getSchId()));
                if (unlockSchool1 == null) {
                    unlockSchoolMapper.insert(unlockSchool);
                } else {
                    unlockSchoolMapper.update(unlockSchool, new UpdateWrapper<StEnergizeUserUnlockSchool>().eq("seu_id", unlockSchool1.getSeuId()));
                }

                System.out.println("---------------------------------------------------------------------------------------------------------------------");
                System.out.println("--------------------------------------------微信回调成功！！！！！！-------------------------------------------------");
                System.out.println("---------------------------------------------------------------------------------------------------------------------");
                return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 微信支付统一下单接口
     *
     * @param request
     * @return
     */
    @Override
    public ResponseJsonCode wxPay(PayDto pay, HttpServletRequest request) {
        //判断传入的参数
        JudgeNumberUtil.isPositiveNumericIntegration(pay.getSchId());
        StringUtil.isNotBlank(pay.getUserNumber());
        StringUtil.isNotBlank(pay.getCtIdentificationCode());
        //随机生成订单号
        String orderNo = System.currentTimeMillis() + "" + pay.getSchId() + "" + (int) ((Math.random() * 9 + 1) * 1000) + "";

        //对一笔订单的具体描述
        StringBuffer body = new StringBuffer();
        String schName = schSchoolMapper.getSchNameAndHeavenNumber(pay.getSchId());
        body.append(schName);
        if (pay.getOption() == 1) {
            body.append("1");
        } else if (pay.getOption() == 2) {
            body.append("7");
        } else if (pay.getOption() == 3) {
            body.append("30");
        }
        body.append("天体验");

        try {
            //插入未支付订单
            StEnergizeOrder energizeOrder = new StEnergizeOrder();
            //UUid
            energizeOrder.setOrderId(UUIDUtil.getUuid());
            //商户id
            energizeOrder.setCtIdentificationCode(pay.getCtIdentificationCode());
            //商户用户账号
            energizeOrder.setUserNumber(pay.getUserNumber());
            //学校id
            energizeOrder.setSchId(pay.getSchId());
            //支付方式(1-支付宝，2-微信)
            energizeOrder.setOrderMode(2);

            if (pay.getOption() == 1) {
                //购买天数
                energizeOrder.setPreOption(1);
            } else if (pay.getOption() == 2) {
                //购买天数
                energizeOrder.setPreOption(7);
            } else if (pay.getOption() == 3) {
                //购买天数
                energizeOrder.setPreOption(30);
            }

            //需付金额
            energizeOrder.setOrderMoney(getSchoolPrice(pay.getSchId(), pay.getOption()));
            //订单创建时间
            energizeOrder.setOrderCreationTime(System.currentTimeMillis() + "");
            //订单编号
            energizeOrder.setOrderReference(orderNo);
            //订单状态(1-已完成，2-未完成)
            energizeOrder.setOrderStatu(2);

            int i = orderMapper.insert(energizeOrder);
            if (i == 0) {
                throw new MyException(ResultEnum.ST_PAY_FEIGN_INTEGRAL_INSERT_FAIL);
            }
            log.info("-----------------------------------------------------------------------------------------------");
            log.info("------------------------------------插入订单成功！！！！！！！---------------------------------");
            log.info("-----------------------------------------------------------------------------------------------");

            //回调地址
            String payment = payment(wechatConfig.getNotify_url(), orderNo, energizeOrder.getOrderMoney().toString(), body.toString());
            if (payment == null) {
                return ResponseJsonCode.errorRequestJsonCode("500", "不好意思，系统开小差了");
            }
            try {
                PayDto wxPay = (PayDto)JedisUtil.getObject("wxPay");
                log.info("redis中的数据："+wxPay);
                return ResponseJsonCode.successRequestMsg("请求成功", payment+"&notify_url="+wechatConfig.getNotify_url());
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseJsonCode.errorRequestJsonCode("500", "不好意思，系统开小差了");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //手动开启事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseJsonCode.errorRequestJsonCode("500", e.getMessage());
        }
    }

    /**
     * <p>方法说明: TODO 统一下单支付处理
     * <p>参数说明: String notifyUrl 回调地址
     * <p>参数说明: String orderNo   订单号
     * <p>参数说明: String totalAmount 订单总金额
     * <p>参数说明: String body  商品描述
     * <p>返回说明: Map<String,Object>
     * <p>创建时间: 2019年4月31日 上午10:47:09
     * <p>创  建  人: zs
     **/
    private String payment(String notifyUrl, String orderNo, String totalAmount, String body) {
        System.out.println("↓↓↓↓↓↓↓↓↓↓微信支付统一下单参数处理↓↓↓↓↓↓↓↓↓↓");
        String unifiedOrderParam = wxpayUtil.getUnifiedOrder(orderNo, totalAmount, notifyUrl, body);
        //回调参数
        Map<String, Object> receiveMap = new HashMap<>(16);
        try {
            log.info("请求的所有参数："+unifiedOrderParam);
            String xmlStr = HttpUtil.doPostToStr(WechatConfig.pay_url, unifiedOrderParam);
            receiveMap = XMLUtil.doXMLParse(xmlStr);
            log.info("微信返回值==" + receiveMap);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("微信支付HTTP请求错误");
            return null;
        }
        //请求返回正常:
        if ("SUCCESS".equals(receiveMap.get("return_code"))) {
            //业务正常:
            if ("SUCCESS".equals(receiveMap.get("result_code"))) {
                log.info("↓↓↓↓↓↓↓↓↓↓微信支付返回APP参数处理↓↓↓↓↓↓↓↓↓↓");
                Map<String, Object> data = new HashMap<>(16);
                System.out.println("拼接的地址："+wxpayUtil.getReturnParam(receiveMap));
                data.put("data", wxpayUtil.getReturnParam(receiveMap));
                System.out.println("地址："+receiveMap.get("mweb_url"));
                return (String) receiveMap.get("mweb_url");
            }
            System.out.println("微信支付请求业务错误:" + receiveMap.get("err_code_des"));
            return null;
        }
        System.out.println("微信支付请求返回错误:" + receiveMap.get("return_msg"));
        return null;
    }



    /**
     * <p>方法说明: TODO 获取学校价格
     * <p>参数说明: String notifyUrl 回调地址
     * <p>参数说明: String body  商品描述
     * <p>返回说明: Map<String,Object>
     * <p>创建时间: 2019年4月31日 上午10:47:09
     * <p>创  建  人: zs
     **/
    private BigDecimal getSchoolPrice(Integer schId, Integer option) {
        BigDecimal mony = null;
        switch (option) {
            case 1:
                mony = slaveMapper.getSchoolPrice("mony_day", schId);
                break;
            case 2:
                mony = slaveMapper.getSchoolPrice("mony_week", schId);
                break;
            case 3:
                mony = slaveMapper.getSchoolPrice("mony_month", schId);
                break;
            default:
                mony = new BigDecimal(0);
        }
        return mony;
    }

}
