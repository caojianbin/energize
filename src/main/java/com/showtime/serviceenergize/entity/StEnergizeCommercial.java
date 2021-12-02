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
 * 书唐赋能商户管理表
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StEnergizeCommercial extends Model<StEnergizeCommercial> {

    private static final long serialVersionUID = 1L;

    /**
     * 商户编号
     */
    private String ctId;

    /**
     * 商户名称
     */
    private String ctName;

    /**
     * 商户号
     */
    private String ctIdentificationCode;

    /**
     * 商户联系人
     */
    private String ctPrincipal;

    /**
     * 商户负责人联系号码
     */
    private String ctPhoneNumber;

    /**
     * 商户所在地
     */
    private String ctAddress;

    /**
     * 商户使用状态（1-正常，2-注销）
     */
    private Integer ctStatus;

    /**
     * 有效起始日期
     */
    private String ctStartTime;

    /**
     * 有效结束日期
     */
    private String ctEndTime;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改人
     */
    private String updateName;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 商户自增编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
