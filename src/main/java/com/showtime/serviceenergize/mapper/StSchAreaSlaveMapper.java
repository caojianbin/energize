package com.showtime.serviceenergize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.showtime.serviceenergize.entity.StSchArea;
import com.showtime.serviceenergize.entity.vo.StaDressVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author zs
 * @Date 2019/2/16 11:10
 * @Description //TODO 学校校区信息mapper(从)
 */
@Mapper
public interface StSchAreaSlaveMapper extends BaseMapper<StSchArea> {
    /**
     * 根据校区ID查询校区详情
     * @param saId
     * @return
     */
    @Select("SELECT sa_id,sch_id,logo,sa_name," +
            "sa_en_name,intro,sa_bg_img," +
            "sa_bg_audio,school_motto,adress,found,sch_property,academy_cnt,major_cnt,sa_province,sa_city,sa_county," +
            "student_cnt,acreage,mater_pnt,doctor_pnt,sex_ratio,intro_voice,sa_type" +
            " FROM st_sch_area WHERE sa_id=#{saId}")
    StSchArea selectStSchAreaSlaveById(Integer saId);

    /**
     * 根据校区ID查询校区的音频简介
     * @param saId
     * @return
     */
    @Select("SELECT intro_voice,sa_id " +
            " FROM st_sch_area WHERE sa_id=#{saId}")
    StSchArea selectStSchAreaVoiceSlaveById(Integer saId);

    /**
     * 根据校区ID查询部分校区详情
     * @param saId
     * @return
     */
    @Select("SELECT sa_id,sch_id,logo,sa_name," +
            "sa_en_name,sa_bg_img," +
            "sa_bg_audio,school_motto,adress,found,sch_property,academy_cnt,major_cnt,sa_province,sa_city,sa_county, " +
            "student_cnt,acreage,mater_pnt,doctor_pnt,sex_ratio,intro_voice,sa_type" +
            " FROM st_sch_area WHERE sa_id=#{saId}")
    StSchArea selectStSchAreaWebSlaveById(Integer saId);

    /**
     * 根据学校的ID查询校区的地图ID等简单信息
     * @param schId   校区ID
     * @param saType 校区类型
     * @return
     */
    @Select({"<script> SELECT sa_id saId,sch_id schId,sa_name saName,sa_en_name saEnName,intro_voice introVoice,mp_id mpId,sa_type saType,sa_campus_img saCampusImg " +
            "FROM st_sch_area WHERE " +
            "<if  test=\"schId!=null \"> sch_id = #{schId} </if>" +
            "<if  test= \"saType !=0 \">  AND sa_type=#{saType} </if>" +
            " </script>"})
    List<Map<String,Object>> selectStSchAreaMapBySchoolId(@Param("schId") Integer schId, @Param("saType") Integer saType);

    /**
     * 根据学校ID获取主校区ID
     * @param schId
     * @return
     */
    @Select("SELECT\n" +
            " sa_id saId\n" +
            "FROM\n" +
            " st_sch_area\n" +
            "WHERE\n" +
            " sch_id = #{schId}\n" +
            "AND sa_type = 1")
    Integer selectStSchAreaMainCampus(Integer schId);


    /**
     * 根据学校ID查询校区详情的信息
     * @param schId
     * @return
     */
    @Select("SELECT  ssa.sa_id,  ssa.sch_id,  ssa.logo, ssa.sa_name,  ssa.sa_en_name,  ssa.intro,  ssa.sa_bg_img, ssa.sa_bg_audio, ssa.school_motto,  \n"+
            "ssa.adress, ssa.found, ssa.sch_property,  ssa.academy_cnt,  ssa.major_cnt,  ssa.student_cnt, ssa.acreage, ssa.mater_pnt,  ssa.doctor_pnt,  \n"+
            "ssa.sex_ratio,  ssa.intro_voice,  ssa.sa_type,  ssa.mp_id,  ssa.sa_campus_img,  sss.sch_name,sss.sch_icon,\n"+
            "(SELECT d_name sa_province\n"+
            "  FROM  st_sys_dictionary\n"+
            "  WHERE ssa.sa_province = d_code\n"+
            " ) sa_province,\n"+
            "( SELECT d_name sa_city\n"+
            "  FROM st_sys_dictionary\n"+
            "  WHERE ssa.sa_city = d_code\n"+
            ") sa_city,\n"+
            "( SELECT d_name sa_county\n"+
            "  FROM st_sys_dictionary\n"+
            "  WHERE ssa.sa_county = d_code\n"+
            ") sa_county,\n"+
            "(\n"+
            "SELECT COUNT(major.sm_id) as majorCount \n"+
            "FROM st_sch_major major\n"+
            "LEFT JOIN st_sch_college college ON college.sc_id = major.sc_id\n"+
            "WHERE college.sch_id = sss.sch_id \n"+
            ") as majorCount \n"+
            "FROM st_sch_area ssa\n"+
            " RIGHT JOIN st_sch_school sss ON ssa.sch_id = sss.sch_id\n"+
            " WHERE ssa.sa_type = 1 AND sss.sch_id =#{schId}")
    StSchArea selectAreaDetailById(Integer schId);


    /**
     * 根据学校ID查询所有校区名称列表
     * @param schId
     * @return
     */
    @Select("SELECT\n" +
            " sa_id saId,\n" +
            " sch_id schId,\n" +
            " sa_name saName,\n" +
            " sa_en_name saEnName,\n" +
            " sa_type saType,\n" +
            " mp_id mpId\n" +
            "FROM\n" +
            " st_sch_area\n" +
            "WHERE\n" +
            " sch_id = #{schId}")
    List<Map<String, Object>> selectStCampusNameLists(@Param("schId") Integer schId);

    /**
     * 根据校区ID查询校区详情的信息
     * @param saId
     * @return
     */
    @Select("SELECT\n" +
            " ssa.sa_id,\n" +
            " ssa.sch_id,\n" +
            " ssa.logo,\n" +
            " ssa.sa_name,\n" +
            " ssa.sa_en_name,\n" +
            " ssa.intro,\n" +
            " ssa.sa_bg_img,\n" +
            " ssa.sa_bg_audio,\n" +
            " ssa.school_motto,\n" +
            " ssa.adress,\n" +
            " ssa.sa_province,\n" +
            " ssa.sa_city,\n" +
            " ssa.sa_county,\n" +
            " ssa.found,\n" +
            " ssa.sch_property,\n" +
            " ssa.academy_cnt,\n" +
            " ssa.major_cnt,\n" +
            " ssa.student_cnt,\n" +
            " ssa.acreage,\n" +
            " ssa.mater_pnt,\n" +
            " ssa.doctor_pnt,\n" +
            " ssa.sex_ratio,\n" +
            " ssa.intro_voice,\n" +
            " ssa.sa_type,\n" +
            " ssa.mp_id,\n" +
            " ssa.sa_campus_img,\n" +
            " sss.sch_name,\n" +
            " sss.sch_icon\n" +
            "FROM\n" +
            " st_sch_area ssa\n" +
            "RIGHT JOIN st_sch_school sss ON ssa.sch_id = sss.sch_id\n" +
            "WHERE\n" +
            " ssa.sa_id = #{saId}")
    StSchArea selectStSchAreaBySaId(Integer saId);


    /**
     * 查询学校地址
     *
     * @param saId 校区id
     * @return
     */
    @Select("SELECT\n" +
            " ssa.adress,\n" +
            " sp.st_provinceName,\n" +
            " sp.st_cityName,\n" +
            " sp.st_areaName\n" +
            "FROM\n" +
            " st_sch_area ssa\n" +
            "LEFT JOIN st_procode sp ON ssa.sa_county = sp.st_areaCode\n" +
            "WHERE\n" +
            " sa_id = #{saId}")
    StaDressVo selectDressBySaId(@Param("saId") Integer saId);
}
