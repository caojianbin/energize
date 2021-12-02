package com.showtime.serviceenergize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.showtime.serviceenergize.entity.StSchMajor;
import com.showtime.serviceenergize.entity.vo.StSchMajorVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author zs
 * @Date 2019/2/18 17:40
 * @Description //TODO 院系专业信息mapper(从)
 */
@Mapper
public interface StSchMajorSlaveMapper extends BaseMapper<StSchMajor> {
    /**
     * 根据学校ID查询优势专业信息
     * @param scId
     * @return
     */
    @Select("SELECT\n" +
            " em_id,\n" +
            " sm_name,\n" +
            " sm_code\n" +
            "FROM\n" +
            "st_sch_college ssc LEFT JOIN \n" +
            " st_sch_major ssm ON ssc.sc_id = ssm.sc_id\n" +
            "WHERE\n" +
            " ssc.sch_id = #{scId}\n" +
            "AND ssm.is_advantage = 1")
    @Results({
            @Result(property = "stExamMajor",column = "em_id",
                    one = @One(select = "com.showtime.serviceschool.mapper.slaveSource.StExamMajorSlaveMapper.selectStExamMajorById"))
    })
    List<StSchMajor> selectAdvantageStSchMajorListBySchId(Integer scId);

    /**
     * 根据学校ID查询专业信息
     * @param scId 院系ID
     * @return
     */
    @Select("SELECT sm_id,sc_id,em_id,is_advantage,sm_order,sm_grade,sm_name,sm_code,sm_detail " +
            " FROM st_sch_major " +
            "WHERE sc_id=#{scId} ")
    @Results({
            @Result(property = "stExamMajor",column = "em_id",
                    one = @One(select = "com.showtime.serviceschool.mapper.slaveSource.StExamMajorSlaveMapper.selectStExamMajorById"))
    })
    List<StSchMajor> selectStSchMajorListByScId(Integer scId);

    //暂用写死
    @Select("SELECT\n" +
            " sm_name\n" +
            "FROM\n" +
            " st_sch_college ssc\n" +
            "LEFT JOIN st_sch_major ssm ON ssc.sc_id = ssm.sc_id\n" +
            "WHERE\n" +
            " ssc.sch_id = #{schId}\n" +
            "LIMIT 0,6")
    List<StSchMajorVo> selectMajorNameList(@Param("schId") Integer schId);


}
