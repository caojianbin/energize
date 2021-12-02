package com.showtime.serviceenergize.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * PayDto
 * </p>
 *
 * @author cjb
 * @since 8:38
 */
@Data
public class PayDto implements Serializable {
    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id")
    private Integer schId;
    /**
     * 交易天数
     */
    @ApiModelProperty(value = "交易天数")
    private Integer option;

    /**
     * 商户id编号
     */
    @ApiModelProperty(value = "商户号（*修改字段,之前為：ctId）")
    private String ctIdentificationCode;

    /**
     * 商户用户账号
     */
    @ApiModelProperty(value = "商户用户账号")
    private String userNumber;

    /**
     * 支付方式(1-支付宝，2-微信)
     */
    @ApiModelProperty(value = "支付方式(1-支付宝，2-微信)")
    private Integer orderMode;

    /**
     * 招生代码
     */
    @ApiModelProperty(value = "招生代码")
    private String schAdmissionCode;

    /**
     *  如果为（ - index：首页入口 - details：单个学校详情页面）
     */
    @ApiModelProperty(value = "如果为（ - index：首页入口 - details：单个学校详情页面）")
    private String point;
}
