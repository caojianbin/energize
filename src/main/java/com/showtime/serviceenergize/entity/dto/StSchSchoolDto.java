package com.showtime.serviceenergize.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 学校信息表
 * </p>
 *
 * @author cjb
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StSchSchoolDto extends Model<StSchSchoolDto> {

    private static final long serialVersionUID = 1L;

    /**
     * 学校主键
     */
    @TableId(value = "sch_id", type = IdType.AUTO)
    @ApiModelProperty(value = "学校主键")
    private Integer schId;

    /**
     * 学校名称
     */
    @ApiModelProperty(value = "学校名称")
    private String schName;

    /**
     * 学校名字拼音
     */
    @ApiModelProperty(value = "学校名字拼音")
    private String schSpellCharacter;

    /**
     * 收藏量
     */
    @ApiModelProperty(value = "收藏量")
    private Integer favoriteCnt;

    /**
     * 解锁量
     */
    @ApiModelProperty(value = "解锁量")
    private Integer unlockCnt;

    /**
     * 可视化状态：0-不可用；1-可用
     */
    @ApiModelProperty(value = "可视化状态：0-不可用；1-可用")
    private Integer visualSts;

    /**
     * 学校logo
     */
    @ApiModelProperty(value = "学校logo")
    private String schLogo;

    /**
     * 学校背景图
     */
    @ApiModelProperty(value = " 学校背景图")
    private String schBgImg;

    /**
     * 学校简介
     */
    @ApiModelProperty(value = "学校简介")
    private String schDesc;

    /**
     * 学校卡片内容（图片/视频）滑动卡片
     */
    @ApiModelProperty(value = "学校卡片内容（图片/视频）滑动卡片")
    private String schCardCt;

    /**
     * 1内地   2港澳台   3海外
     */
    @ApiModelProperty(value = "1内地   2港澳台   3海外")
    private Integer schAddreType;

    /**
     * 学校解锁积分
     */
    @ApiModelProperty(value = "学校解锁积分")
    private Integer unlockPrice;

    /**
     * 是否热门   1是   0否
     */
    @ApiModelProperty(value = "是否热门   1是   0否")
    private Integer isHot;

    /**
     * 学校排名
     */
    @ApiModelProperty(value = "学校排名")
    private Integer schOrder;

    /**
     * 可配置排序（）
     */
    @ApiModelProperty(value = "可配置排序")
    private Integer cusOrder;

    /**
     * 是否有可视化地图  1有  0否
     */
    @ApiModelProperty(value = "是否有可视化地图  1有  0否")
    private String isPlat;

    /**
     * 学校个性小图标
     */
    @ApiModelProperty(value = "学校个性小图标")
    private String schIcon;

    /**
     * 高校所在市区
     */
    @ApiModelProperty(value = "高校所在市区")
    private String schCity;

    /**
     * 解锁一天价格
     */
    @ApiModelProperty(value = "解锁一天价格")
    private BigDecimal monyDay;

    /**
     * 解锁一周价格
     */
    @ApiModelProperty(value = "解锁一周价格")
    private BigDecimal monyWeek;

    /**
     * 解锁一月价格
     */
    @ApiModelProperty(value = "解锁一月价格")
    private BigDecimal monyMonth;

    /**
     * 学校入驻时间
     */
    @ApiModelProperty(value = "学校入驻时间")
    private LocalDateTime schEstablishTime;

    /**
     * 认证状态
     */
    @ApiModelProperty(value = "认证状态")
    private Integer stCertificationStatus;

    /**
     * 浏览量
     */
    @ApiModelProperty(value = "浏览量")
    private String stPageView;

    /**
     * 小程序分享图片
     */
    @ApiModelProperty(value = "小程序分享图片")
    private String stQrCodeSharePictures;

    /**
     * 運營人員id
     */
    @ApiModelProperty(value = "運營人員id")
    private Integer stUid;

    /**
     * 永久激活二维码
     */
    @ApiModelProperty(value = "永久激活二维码")
    private String shcQrCode;

    /**
     * 是否上线  1已上线  2未上线  3待审核
     */
    @ApiModelProperty(value = "是否上线  1已上线  2未上线  3待审核")
    private Integer schIsOnline;

    /**
     * 高校权重（默认100）
     */
    @ApiModelProperty(value = "高校权重（默认100）")
    private Integer schWeight;

    /**
     * 招生代码
     */
    @ApiModelProperty(value = "招生代码")
    private String schAdmissionCode;

    @Transient
    @Column(name = "unlock_status")
    @ApiModelProperty(value = "用户是否解锁 默认0未解锁， 1解锁", required = true)
    private Integer unlockStatus = 0;

    @Override
    protected Serializable pkVal() {
        return this.schId;
    }

}
