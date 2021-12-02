package com.showtime.serviceenergize.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 赋能管理后台用户表
 * </p>
 *
 * @author cjb
 * @since 2019-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StEmpowermentUserTable extends Model<StEmpowermentUserTable> {

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
     * 用户昵称
     */
    private String userUserName;

    /**
     * 用户手机号,登录账号
     */
    private String userPhone;

    /**
     * 用户登录密码
     */
    private String userPassWord;

    /**
     * 用户登录密码随机盐
     */
    private String userSalt;

    /**
     * 用户状态   1-启用  2-禁用   3-删除
     */
    private String userStatu;

    /**
     * 用户头像
     */
    private String userHeardImg;

    /**
     * 用户最后登陆时间
     */
    @TableField("user_lastLoginTime")
    private String userLastlogintime;

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
