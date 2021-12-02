package com.showtime.serviceenergize.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * StEnergizeLogVo
 * </p>
 *
 * @author cjb
 * @since 10:57
 */
@Data
@ApiModel("日志管理")
public class StEnergizeLogVo {
    /**
     * 日志id
     */
    @ApiModelProperty(value = "日志自增编号")
    private String logId;

    /**
     * 日志类型
     */
    @ApiModelProperty(value = "日志类型")
    private String logType;

    /**
     * 日志标题
     */
    @ApiModelProperty(value = "日志标题")
    private String logTitle;

    /**
     * 请求地址
     */
    @ApiModelProperty(value = "请求地址")
    private String logRequestUri;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式")
    private String logMethod;

    /**
     * IP
     */
    @ApiModelProperty(value = "IP")
    private String logIp;

    /**
     * 提交参数
     */
    @ApiModelProperty(value = "提交参数")
    private String logParams;

    /**
     * 异常
     */
    @ApiModelProperty(value = "异常")
    private String logException;

    /**
     * 耗时
     */
    @ApiModelProperty(value = "耗时")
    private String logTimeout;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String userId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 角色类型
     */
    @ApiModelProperty(value = "角色类型")
    private Integer roleType;

    /**
     * 日志id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "日志id")
    private Integer id;
}
