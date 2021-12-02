package com.showtime.serviceenergize.entity.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * StEnergizeOrderVo
 * </p>
 *
 * @author cjb
 * @since 18:22
 */
@Data
@ApiModel("订单管理查询结果")
public class StEnergizeOrderVo {
    /**
     * 交易流水
     */
    private String tranOrderNum;
    /**
     * 激活高校
     */
    private String schName;
    /**
     * 交易号
     */
    private String orderReference;
    /**
     * 商户名称
     */
    private String ctName;
    /**
     * 支出用户
     */
    private String userNumber;
    /**
     * 消费时间
     */
    private String orderPayTime;
    /**
     * 消费金额
     */
    private BigDecimal payMoney;
}
