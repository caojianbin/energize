package com.showtime.serviceenergize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.showtime.serviceenergize.entity.StSchSchool;
import com.showtime.serviceenergize.entity.dto.HomeSchoolDto;
import com.showtime.serviceenergize.entity.dto.StSchSchoolDto;
import com.showtime.serviceenergize.entity.dto.StSchoolAdmissionCodeDto;
import com.showtime.serviceenergize.entity.vo.FuzzyQuerySchoolVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 学校信息表 Mapper 接口
 * </p>
 *
 * @author cjb
 * @since 2019-12-26
 */
public interface StSchSchoolMapper extends BaseMapper<StSchSchool> {

    /**
     * 分页查询学校列表信息
     *
     * @return
     */
    List<HomeSchoolDto> getSchSchoolList(
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize,
            @Param("isPlat") Integer isPlat);


    /**
     * 根据学校名称和拼音查询（这个不能使用，使用就报元素值必须为常量表达式）
     */
    String SCH_NAME =
            "<where>" +
                    "   sc.visual_sts= #{visualSts} " +
                    "   <if test='schName != null and schName != \"\"'>  and sc.sch_name like '%${schName}%' </if>" +
                    "   <if test='schNameSpell != null and schNameSpell != \"\"'>  and sc.sch_spell_character like '%${schNameSpell}%' </if>" +
                    "</where>";

    /**
     * 根据学校名称模糊查询（书堂录）
     *
     * @return
     */
    @Select("<script>" +
            "SELECT\n" +
            "\tsc.sch_id ,\n" +
            "\tsc.sch_name ,\n" +
            "\tsc.sch_is_online ,\n" +
            "\tsc.is_plat \n" +
            "FROM\n" +
            "\t`st_sch_school` sc\n" +
            SCH_NAME +
            "ORDER BY\n" +
            "\tsc.is_plat DESC,\n" +
            "\tsc.sch_order ASC\n" +
            "LIMIT #{pageNumMy},#{pageSizeMy}" +
            "</script>")
    List<FuzzyQuerySchoolVo> selectStSchoolNameListBySchName(@Param(value = "visualSts") Integer visualSts,
                                                             @Param(value = "schName") String schName,
                                                             @Param(value = "schNameSpell") String schNameSpell,
                                                             @Param(value = "pageNumMy") Integer pageNumMy,
                                                             @Param(value = "pageSizeMy") Integer pageSizeMy);

    @Select("SELECT\n" +
            "sch_name\n" +
            "FROM\n" +
            "st_sch_school\n" +
            "WHERE\n" +
            "sch_id = #{schId}")
    String getSchNameAndHeavenNumber(@Param("schId") Integer schId);

    /**
     * 根据学校编码查询
     *
     * @param schAdmissionCode 学校编码
     * @return
     */
    @Select("select * from st_sch_school WHERE sch_admission_code = #{schAdmissionCode}")
    StSchSchoolDto selectSchoolByCoding(@Param("schAdmissionCode") String schAdmissionCode);

    /**
     * 查询所有学校编码
     *
     * @return
     */
    StSchoolAdmissionCodeDto selectSchoolCoding(@Param("schAdmissionCode") String schAdmissionCode);
}
