package com.showtime.serviceenergize.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @Author zs
 * @Date 2019/5/5 9:32
 * @Description //TODO 获取学校价格信息
 */
@Mapper
public interface GetSchoolPriceSlaveMapper {

    @Select("SELECT\n" +
            "${type}\n" +
            "FROM\n" +
            "st_sch_school\n" +
            "WHERE\n" +
            "sch_id = #{schId}")
    BigDecimal getSchoolPrice(
            @Param("type") String type,
            @Param("schId") Integer schId);


    @Select("SELECT\n" +
            "mony_day ,\n" +
            "mony_week ,\n" +
            "mony_month \n" +
            "FROM\n" +
            "st_sch_school\n" +
            "WHERE\n" +
            "sch_id = #{schId}")
    Map<String, Map<String , Object>> selectSchoolPriceList(Integer schId);

    @Select("SELECT\n" +
            "sch_name\n" +
            "FROM\n" +
            "st_sch_school\n" +
            "WHERE\n" +
            "sch_id = #{schId}")
    String getSchNameAndHeavenNumber(@Param("schId") Integer schId);
}
