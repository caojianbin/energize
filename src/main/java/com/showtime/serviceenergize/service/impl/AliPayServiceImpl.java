package com.showtime.serviceenergize.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.common.utils.JudgeNumberUtil;
import com.showtime.serviceenergize.config.AlipayConfig;
import com.showtime.serviceenergize.entity.StEnergizeOrder;
import com.showtime.serviceenergize.entity.StEnergizeUserUnlockSchool;
import com.showtime.serviceenergize.entity.dto.PayDto;
import com.showtime.serviceenergize.mapper.GetSchoolPriceSlaveMapper;
import com.showtime.serviceenergize.mapper.StEnergizeOrderMapper;
import com.showtime.serviceenergize.mapper.StEnergizeUserUnlockSchoolMapper;
import com.showtime.serviceenergize.service.AliPayService;
import com.showtime.serviceenergize.utils.DateUtil;
import com.showtime.serviceenergize.utils.JedisUtil;
import com.showtime.serviceenergize.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author cjb
 * @Date 2020/1/2 15:15
 * @Description //TODO 支付宝支付的service实现类
 */
@Transactional(rollbackFor = Exception.class)
@Slf4j
@Service
public class AliPayServiceImpl implements AliPayService {
    /**
     * 支付宝支付的一些配置
     */
    @Autowired
    private AlipayConfig alipayConfig;
    /**
     * 获取学校价格信息
     */
    @Autowired
    private GetSchoolPriceSlaveMapper slaveMapper;
    /**
     * 订单表 Mapper
     */
    @Autowired
    private StEnergizeOrderMapper orderMapper;
    /**
     * 用户解锁时限表 Mapper
     */
    @Autowired
    private StEnergizeUserUnlockSchoolMapper unlockSchoolMapper;

    /**
     * 创建订单
     * @return
     * @throws Exception
     */
    @Override
    public ResponseJsonCode create(PayDto pay) throws Exception {
        JudgeNumberUtil.isPositiveNumericIntegration(pay.getSchId());
        JudgeNumberUtil.isPositiveNumeric(pay.getUserNumber());
        JudgeNumberUtil.isPositiveNumeric(pay.getCtIdentificationCode());
        //将订单参数存入redis中，在支付宝支付成功时候调用
        JedisUtil.setObject("alipay",pay);
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayConfig.getGatewayUrl(),
                alipayConfig.getApp_id(),
                alipayConfig.getMerchant_private_key(),
                "json",
                "utf-8",
                alipayConfig.getAlipay_public_key(),
                "RSA2");
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //随机生成订单号
        String orderNo = System.currentTimeMillis() + "" + pay.getUserNumber() + "" + (int) ((Math.random() * 9 + 1) * 1000) + "";
        //------------------------------------------封装参数开始--------------------------------------------
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setSubject("可视化地图解锁");
        model.setOutTradeNo(orderNo);
        model.setTotalAmount(getSchoolPrice(pay.getSchId(), pay.getOption()) + "");
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setBody("购买可视化校园地图");
        request.setBizModel(model);
        request.setReturnUrl(alipayConfig.getReturn_url());
        request.setNotifyUrl(alipayConfig.getNotify_url());
        AlipayTradeWapPayResponse wapPayResponse = alipayClient.pageExecute(request);
        if (wapPayResponse.isSuccess()) {
            String body = wapPayResponse.getBody();
            //将信息存入订单，订单对象
            StEnergizeOrder order = new StEnergizeOrder();
            //UUid
            order.setOrderId(UUIDUtil.getUuid());
            //订单创建时间
            order.setOrderCreationTime(System.currentTimeMillis()+"");
            //商户好
            order.setCtIdentificationCode(pay.getCtIdentificationCode());
            //商户用户账号
            order.setUserNumber(pay.getUserNumber());
            //学校id
            order.setSchId(pay.getSchId());
            //支付方式(1-支付宝，2-微信)
            order.setOrderMode(pay.getOrderMode());
            if (pay.getOption() == 1) {
                //购买天数
                order.setPreOption(1);
            } else if (pay.getOption() == 2) {
                //购买天数
                order.setPreOption(7);
            } else if (pay.getOption() == 3) {
                //购买天数
                order.setPreOption(30);
            }

            //需付金额
            order.setOrderMoney(getSchoolPrice(pay.getSchId(), pay.getOption()));

            //订单编号
            order.setOrderReference(orderNo);
            //订单状态(1-已完成，2-未完成)
            order.setOrderStatu(2);
            orderMapper.insert(order);
            return ResponseJsonCode.successRequestMsg("请求成功", body);
        } else {
            return null;
        }
    }


    /**
     * 回调参数
     *
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public String notify(HttpServletResponse response, HttpServletRequest request) throws Exception {
        // 交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> paramsMap = new HashMap<>(16);
            requestMap.forEach((key, values) -> {
                String strs = "";
                for (String value : values) {
                    strs = strs + value;
                }
                paramsMap.put(key, strs);
            });
            //根据订单号获取订单
            StEnergizeOrder order = orderMapper.selectOne(new QueryWrapper<StEnergizeOrder>().eq("order_reference", paramsMap.get("out_trade_no")));
            //将用户解锁信息存入
            StEnergizeUserUnlockSchool unlockSchool = new StEnergizeUserUnlockSchool();
            if (order != null) {
                //交易流水号
                order.setTranOrderNum(paramsMap.get("trade_no"));
                //实付金额
                String tradeNo = paramsMap.get("total_amount");
                if (tradeNo != null) {
                    BigDecimal payMoney = new BigDecimal(tradeNo);
                    order.setPayMoney(payMoney);
                }
                //订单创建时间
                String gmtCreate = paramsMap.get("gmt_create");
                order.setOrderCreationTime(DateUtil.timesTamp(gmtCreate));
                //解锁时间
                String gmtPayment = paramsMap.get("gmt_payment");
                //将时间格式转为时间戳
                String timesTamp = DateUtil.timesTamp(gmtPayment);
                order.setPurchasingTime(timesTamp);
                unlockSchool.setSeuStartTime(timesTamp);
                //过期时间
                Calendar calendar = Calendar.getInstance();
                //插入到期时间
                Date unlockTime = DateUtil.strDate(gmtPayment);
                calendar.setTime(unlockTime);
                Date time = null;
                String toStr = null;
                if (1 == order.getPreOption()) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    time = calendar.getTime();
                    toStr = DateUtil.dateToStr(time);
                    order.setExpirationTime(DateUtil.timesTamp(toStr));
                    unlockSchool.setSeuEndTime(DateUtil.timesTamp(toStr));
                } else if (7 == order.getPreOption()) {
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                    time = calendar.getTime();
                    toStr = DateUtil.dateToStr(time);
                    order.setExpirationTime(DateUtil.timesTamp(toStr));
                    unlockSchool.setSeuEndTime(DateUtil.timesTamp(toStr));
                } else if (30 == order.getPreOption()) {
                    calendar.add(Calendar.DAY_OF_MONTH, 30);
                    time = calendar.getTime();
                    toStr = DateUtil.dateToStr(time);
                    order.setExpirationTime(DateUtil.timesTamp(toStr));
                    unlockSchool.setSeuEndTime(DateUtil.timesTamp(toStr));
                }
                //订单支付时间
                String payTime = paramsMap.get("gmt_payment");
                String payDateTime = DateUtil.timesTamp(payTime);
                order.setOrderPayTime(payDateTime);
                //订单状态(1-已完成，2-未完成)
                order.setOrderStatu(1);
                //修改对象
                orderMapper.update(order, new UpdateWrapper<StEnergizeOrder>().eq("order_reference", paramsMap.get("out_trade_no")));

                unlockSchool.setCtIdentificationCode(order.getCtIdentificationCode());
                unlockSchool.setSchId(order.getSchId());
                unlockSchool.setUserNumber(order.getUserNumber());
                unlockSchool.setSeuId(UUIDUtil.getUuid());
                //将用户解锁信息存入\修改
                StEnergizeUserUnlockSchool unlockSchool1 = unlockSchoolMapper.selectOne(new QueryWrapper<StEnergizeUserUnlockSchool>().eq("ct_identification_code", order.getCtIdentificationCode()).eq("user_number", order.getUserNumber()).eq("sch_id",order.getSchId()));
                if (unlockSchool1 == null){
                    unlockSchoolMapper.insert(unlockSchool);
                }else {
                    unlockSchoolMapper.update(unlockSchool,new UpdateWrapper<StEnergizeUserUnlockSchool>().eq("seu_id",unlockSchool1.getSeuId()));
                }

            } else {
                return "fail";
            }
//            //调用SDK验证签名
//            try {
//                boolean whether = AlipaySignature.rsaCheckV1(paramsMap, alipayConfig.getAlipay_public_key(), alipayConfig.getCharset(), alipayConfig.getSign_type());
//            } catch (AlipayApiException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return "fail";
//            }
            return "success";
        }
        return "fail";
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
