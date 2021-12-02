package com.showtime.serviceenergize.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p>
 * StaDressVo
 * </p>
 *
 * @author cjb
 * @since 17:52
 */
@Data
@ApiModel("学校详细地址")
public class StaDressVo {
    /**
     * 详细地址
     */
    private String adress;
    /**
     * 省份
     */
    private String stProvinceName;
    /**
     * 市区
     */
    private String stCityName;
    /**
     * 区域
     */
    private String stAreaName;
}
