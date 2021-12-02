package com.showtime.serviceenergize.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * @Author hjq
 * @Date 06/06/2019 13:19
 * @Description //TODO 返回给前端的主页校区DTO
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HomeSchoolDto {

    /**
     * 学校主键
     */
    @Id
    @Column(name = "`sch_id`")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "学校主键ID", hidden = true)
    private Integer schId;

    /**
     * 学校名称
     */
    @Column(name = "`sch_name`")
    @ApiModelProperty(value = "学校名称", required = true,example = "0")
    private String schName;



    /**
     * 解锁量
     */
    @Column(name = "`unlock_cnt`")
    @ApiModelProperty(value = "解锁量", hidden = true)
    private Integer unlockCnt;

    /**
     * 学校解锁积分
     */
    @Column(name = "`unlock_price`")
    @ApiModelProperty(value = "学校解锁积分 列子：100", required = true)
    private Integer unlockPrice;

    /**
     * 是否有可视化地图  1有  0否
     */
    @Column(name = "`is_plat`")
    @ApiModelProperty(value = "是否有可视化地图  1有  0否", required = true,example = "0")
    private String isPlat;

    /**
     * 是否认证
     */
    @Column(name = "`is_certification`")
    @ApiModelProperty(value = "0  未认证  1 已认证", required = true,example = "0")
    private String isCertification;

    /**
     * 校区ID
     */
    @Column(name = "sa_id")
    @ApiModelProperty(value = "校区ID", required = true)
    private Integer saId;

    /**
     * 学校资源背景图片
     */
    @Column(name = "sch_bg_img")
    @ApiModelProperty(value = "学校资源背景图片", required = true,example = "0")
    private String schBgImg;

    /**
     * 高校所在市区
     */
    private String saCity;
    /**
     * 高校所在省份
     */
    private String saProvince;
    /**
     * 高校所在区域名字
     */
    @Transient
    private String schCity;

    /**
     * 学校资源背景视频
     */
    @Column(name = "sa_mp4")
    @ApiModelProperty(value = "学校资源背景视频", required = true)
    private String saMp4;

    @Transient
    @Column(name = "unlock_status")
    @ApiModelProperty(value = "用户是否解锁 默认0未解锁， 1解锁", required = true)
    private Integer unlockStatus = 0;

    @Column(name = "sch_logo")
    @ApiModelProperty(value = "学校logo", required = true)
    private String schLogo;

    /**
     * 学校校训
     */
    @Column(name = "`school_motto`")
    @ApiModelProperty(value = "学校校训", required = true)
    private String schoolMotto;

    /**
     * 学校个性小图标
     */
    @Column(name = "`sch_icon`")
    @ApiModelProperty(value = "学校个性小图标", required = true)
    private String schIcon;

    /**
     * 是否上线  1：已上线  2：建设中
     */
    @Column(name = "`sch_is_online`")
    @ApiModelProperty(value = "是否上线", required = true)
    private Integer schIsOnline;

    /**
     * 真实浏览量
     */
    @Column(name = "`page_views`")
    @ApiModelProperty(value = "真实浏览量", required = true)
    private Integer pageViews;

    /**
     * 造假浏览量
     */
    @Column(name = "`st_page_view`")
    @ApiModelProperty(value = "造假浏览量", required = true)
    private String stPageView;

    @Transient
    @ApiModelProperty(value = "标签List", required = true)
    private List<String> tagList;

    public Integer getUnlockStatus() {
        return null == unlockStatus ? 0 : unlockStatus;
    }
}
