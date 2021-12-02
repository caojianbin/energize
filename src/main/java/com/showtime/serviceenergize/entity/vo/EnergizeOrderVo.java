package com.showtime.serviceenergize.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * EnergizeOrderVo
 * </p>
 *
 * @author cjb
 * @since 16:26
 */
@Data
public class EnergizeOrderVo {

    /**
     * 总收入
     */
    private BigDecimal money;
    /**
     * 本月收入
     */
    private BigDecimal tisMonthMoney;
    /**
     * 上月收入
     */
    private BigDecimal lastMonthMoney;


}
