package com.showtime.serviceenergize.entity.dto;

import lombok.Data;

/**
 * <p>
 * EmpowermentUserRoleVo
 * </p>
 *
 * @author cjb
 * @since 17:19
 */
@Data
public class EmpowermentUserRoleVo {

    /**
     * 用户名
     */
    private String userUserName;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色code
     */
    private String roleCode;
    /**
     * 角色id
     */
    private Integer id;
}
