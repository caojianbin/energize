package com.showtime.serviceenergize.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 书唐赋能订单表
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StEnergizeOrder extends Model<StEnergizeOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 赋能订单id编号
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderReference;

    /**
     * 商户id编号
     */
    private String ctIdentificationCode;

    /**
     * 商户用户账号
     */
    private String userNumber;

    /**
     * 订单创建时间
     */
    private String orderCreationTime;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 实付款金额
     */
    private BigDecimal payMoney;

    /**
     * 交易订单号
     */
    private String tranOrderNum;

    /**
     * 订单支付时间
     */
    private String orderPayTime;

    /**
     * 订单状态
     */
    private Integer orderStatu;

    /**
     * 学校id
     */
    private Integer schId;

    /**
     * 支付方式(1-支付宝，2-微信)
     */
    private Integer orderMode;

    /**
     * 解锁的级别  1：日      2：周        3：月
     */
    private Integer preOption;

    /**
     * 购买时间
     */
    private String purchasingTime;

    /**
     * 到期时间
     */
    private String expirationTime;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改人
     */
    private String updateName;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 订单自增编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
