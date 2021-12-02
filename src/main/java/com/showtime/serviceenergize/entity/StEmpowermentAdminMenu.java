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
 * 赋能后台菜单表
 * </p>
 *
 * @author cjb
 * @since 2019-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StEmpowermentAdminMenu extends Model<StEmpowermentAdminMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 当下后台菜单ID
     */
    private String menuId;

    /**
     * 当下后台菜单标题
     */
    private String menuTitle;

    /**
     * 当下后台菜单链接
     */
    private String menuUrl;

    /**
     * 菜单code
     */
    private String menuCode;

    /**
     * 当下后台菜单小图标
     */
    private String menuIcon;

    /**
     * 当下后台父级菜单code
     */
    private String menuParentCode;

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
