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
 * 赋能后台权限表
 * </p>
 *
 * @author cjb
 * @since 2019-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StEmpowermentAdminPermission extends Model<StEmpowermentAdminPermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限ID
     */
    private String permissionId;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限代码
     */
    private String permissionCode;

    /**
     * 权限状态   关联业务字典表
     */
    private String permissionStatu;

    /**
     * 权限父类code
     */
    private String permissionParentCode;

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
