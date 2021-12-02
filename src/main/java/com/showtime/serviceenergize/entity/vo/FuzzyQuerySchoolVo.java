package com.showtime.serviceenergize.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * <p>
 * FuzzyQuerySchoolVo
 * </p>
 *
 * @author cjb
 * @since 9:04
 */
@Data
public class FuzzyQuerySchoolVo {
    /**
     * 学校主键
     */
    @ApiModelProperty(value = "学校主键ID", hidden = true)
    private Integer schId;
    /**
     * 学校名称
     */
    @ApiModelProperty(value = "学校名称", required = true)
    private String schName;
    /**
     * 是否上线  1：已上线  2：建设中
     */
    @ApiModelProperty(value = "是否上线", required = true)
    private Integer schIsOnline;
    /**
     * 是否有可视化地图  1有  0否
     */
    @ApiModelProperty(value = "是否有可视化地图  1有  0否")
    private String isPlat;
    /**
     * 用户是否解锁 默认0未解锁， 1解锁
     */
    @Transient
    @ApiModelProperty(value = "用户是否解锁 默认0未解锁， 1解锁", required = true)
    private Integer unlockStatus = 0;

}
