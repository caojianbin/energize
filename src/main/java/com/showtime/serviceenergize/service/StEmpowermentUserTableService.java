package com.showtime.serviceenergize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.StEmpowermentUserTable;
import com.showtime.serviceenergize.entity.dto.EmpowermentUserRoleVo;
import com.showtime.serviceenergize.entity.vo.StPermissiondVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 赋能管理后台用户表 服务类
 * </p>
 *
 * @author cjb
 * @since 2019-12-31
 */
public interface StEmpowermentUserTableService extends IService<StEmpowermentUserTable> {

    /**
     * 登录接口
     *
     * @param userPhone  账号
     * @param password 密码
     * @return
     */
    ResponseJsonCode login(String userPhone, String password);

    /**
     * 退出登录
     *
     * @return
     */
    void logOut();

    /**
     * 查询用户角色信息
     *
     * @return
     */
    Set<String> selectUserRoleByUserNames(String userName);

    /**
     * 查询用户角色信息
     *
     * @return
     */
    EmpowermentUserRoleVo getUserRoleByUserNames(String userName);
    /**
     * 获取角色对应的权限
     *
     * @param roleId 角色id
     * @return
     */
    Set<String> selectPermissiondListByRole(Integer roleId);
}
