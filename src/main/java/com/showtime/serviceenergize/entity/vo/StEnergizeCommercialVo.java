package com.showtime.serviceenergize.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * StEnergizeCommercialVo
 * </p>
 *
 * @author cjb
 * @since 15:48
 */
@Data
@ApiModel("商户添加")
public class StEnergizeCommercialVo {

    /**
     * 商户名称
     */
    @ApiModelProperty(value = "商户人名",required = false)
    private String ctName;

    /**
     * 商户联系人
     */
    @ApiModelProperty(value = "商户名称",required = true)
    private String ctPrincipal;

    /**
     * 商户负责人联系号码
     */
    @ApiModelProperty(value = "商户负责人联系号码",required = true)
    private String ctPhoneNumber;
    /**
     * 商户所在地
     */
    @ApiModelProperty(value = "商户所在地",required = true)
    private String ctAddress;


    /**
     * 有效起始日期
     */
    @ApiModelProperty(value = "有效起始日期",required = true)
    private String ctStartTime;

    /**
     * 有效结束日期
     */
    @ApiModelProperty(value = "有效结束日期",required = true)
    private String ctEndTime;
}
