package com.showtime.serviceenergize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.showtime.serviceenergize.entity.StProcode;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 书唐省市区代码表 Mapper 接口
 * </p>
 *
 * @author cjb
 * @since 2019-12-23
 */
public interface StProcodeMapper extends BaseMapper<StProcode> {

    /**
     * 查询所有省份
     *
     * @return
     */
    @Select("SELECT DISTINCT st_provinceCode,  st_provinceName\n" +
            "FROM st_procode WHERE st_provinceCode is NOT NULL")
    List<StProcode> selectProvinces();

    /**
     * 查询所有市
     *
     * @return
     */
    @Select("SELECT DISTINCT st_cityCode,  st_cityName\n" +
            "FROM st_procode WHERE st_cityCode is NOT NULL")
    List<StProcode> selectCitys();

    /**
     * 查询所有区
     *
     * @return
     */
    @Select("SELECT DISTINCT st_areaCode,  st_areaName\n" +
            "FROM st_procode WHERE st_areaCode is NOT NULL")
    List<StProcode> selectAreas();

    /**
     * 所有地址
     *
     * @return
     */
    @Select("SELECT st_provinceCode,st_provinceName,st_cityCode,st_cityName,st_areaCode,st_areaName FROM st_procode")
    List<StProcode> selectProcodeList();

}
