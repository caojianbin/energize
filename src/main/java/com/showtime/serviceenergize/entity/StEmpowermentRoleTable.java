package com.showtime.serviceenergize.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 赋能后台角色表
 * </p>
 *
 * @author cjb
 * @since 2019-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StEmpowermentRoleTable extends Model<StEmpowermentRoleTable> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色代码
     */
    private String roleCode;

    /**
     * 角色状态  关联业务字典表
     */
    private String roleStatu;

    /**
     * 记录创建时间
     */
    private String createTime;

    /**
     * 记录创建人
     */
    private String createName;

    /**
     * 记录修改时间
     */
    private String updateTime;

    /**
     * 记录修改人
     */
    private String updateName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
