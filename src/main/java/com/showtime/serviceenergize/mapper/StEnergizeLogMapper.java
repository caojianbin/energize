package com.showtime.serviceenergize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.showtime.serviceenergize.entity.StEnergizeLog;
import com.showtime.serviceenergize.entity.vo.StEnergizeLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 赋能操作日志 Mapper 接口
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
public interface StEnergizeLogMapper extends BaseMapper<StEnergizeLog> {

    /**
     * 查询日志列表
     *
     * @param userName 用户名
     * @param roleType 角色类型
     * @return list 结果集
     */
    List<StEnergizeLogVo> selectEnergizeList(@Param("userName") String userName, @Param("roleType") Integer roleType);
}
