package com.showtime.serviceenergize.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.showtime.serviceenergize.entity.StSchTagInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author xrj（这个表不存在）
 * @Date 2019/2/14 15:43
 * @Description //TODO
 */
@Mapper
public interface StSchTagInfoSlaveMapper extends BaseMapper<StSchTagInfo> {



    @Select("SELECT inf.tag_name " +
            " FROM `st_sch_tag_sch_rel` rel LEFT JOIN st_sch_tag_info inf ON rel.t_id=inf.t_id " +
            " WHERE rel.sch_id= #{schId} ORDER BY inf.t_order")
    List<String> getTagBySchId(@Param(value = "schId") Integer schId);


}
