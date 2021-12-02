package com.showtime.serviceenergize.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 书唐省市区代码表
 * </p>
 *
 * @author cjb
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StProcode extends Model<StProcode> {

    private static final long serialVersionUID = 1L;

    /**
     * 省市区代码表id
     */
    @TableId(value = "st_proid", type = IdType.AUTO)
    private Integer stProid;

    /**
     * 省份代码
     */
    @TableField("st_provinceCode")
    private Integer stProvincecode;

    /**
     *  省份名字
     */
    @TableField("st_provinceName")
    private String stProvincename;

    /**
     * 市区代码
     */
    @TableField("st_cityCode")
    private Integer stCitycode;

    /**
     * 市区名字
     */
    @TableField("st_cityName")
    private String stCityname;

    /**
     * 区域代码
     */
    @TableField("st_areaCode")
    private Integer stAreacode;

    /**
     * 区域名字
     */
    @TableField("st_areaName")
    private String stAreaname;


    @Override
    protected Serializable pkVal() {
        return this.stProid;
    }

}
