package com.showtime.serviceenergize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.showtime.serviceenergize.entity.StEmpowermentUserTable;
import com.showtime.serviceenergize.entity.dto.EmpowermentUserRoleVo;
import com.showtime.serviceenergize.entity.vo.StPermissiondVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 赋能管理后台用户表 Mapper 接口
 * </p>
 *
 * @author cjb
 * @since 2019-12-31
 */
public interface StEmpowermentUserTableMapper extends BaseMapper<StEmpowermentUserTable> {

    /**
     * 根据用户名查询对应的角色
     *
     * @param userName 用户名
     * @return
     */
    List<EmpowermentUserRoleVo> selectUserRoleByUserNames(@Param("userName") String userName);

    /**
     * 根据用户名查询对应的角色
     *
     * @param userName 用户名
     * @return
     */
    EmpowermentUserRoleVo selectUserRoleByUserName(@Param("userName") String userName);

    /**
     * 根据角色id获取对应的权限
     *
     * @param roleId 角色id
     * @return
     */
    List<StPermissiondVo> selectPermissiondListByRole(@Param("roleId") Integer roleId);
}
