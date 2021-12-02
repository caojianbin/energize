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
 * 赋能后台用户角色中间表
 * </p>
 *
 * @author cjb
 * @since 2019-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StEmpowermentUserRoleTable extends Model<StEmpowermentUserRoleTable> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

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
