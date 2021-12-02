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
 * 商户用户解锁时限表
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StEnergizeUserUnlockSchool extends Model<StEnergizeUserUnlockSchool> {

    private static final long serialVersionUID = 1L;

    /**
     * 解锁表id
     */
    private String seuId;

    /**
     * 商户号
     */
    private String ctIdentificationCode;

    /**
     * 用户账号
     */
    private String userNumber;

    /**
     * 学校id
     */
    private Integer schId;

    /**
     * 购买时间
     */
    private String seuStartTime;

    /**
     * 到期时间
     */
    private String seuEndTime;

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
     * 解锁自增编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
