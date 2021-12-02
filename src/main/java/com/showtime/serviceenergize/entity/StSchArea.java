package com.showtime.serviceenergize.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.showtime.serviceenergize.entity.vo.StSchMajorVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author zs
 */
@JsonInclude(value= JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
@ApiModel("")
@Table(name = "`st_sch_area`")
public class StSchArea implements Serializable {
    private static final long serialVersionUID = -2686407536120166797L;
    /**
     * 主键
     */
    @Id
    @Column(name = "`sa_id`")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("主键")
    private Integer saId;

    /**
     * 所属学校id
     */
    @Column(name = "`sch_id`")
    @ApiModelProperty("所属学校id")
    private Integer schId;

    /**
     * logo
     */
    @Column(name = "`logo`")
    @ApiModelProperty("logo")
    private String logo;

    /**
     * 校区名称
     */
    @Column(name = "`sa_name`")
    @ApiModelProperty("校区名称")
    private String saName;

    /**
     * 学校名称
     */
    @Column(name = "`sch_name`")
    @ApiModelProperty("学校名称")
    private String schName;

    /**
     * 学校个性小图标
     */
    @ApiModelProperty("学校个性小图标")
    private String schIcon;

    /**
     * 英文名称
     */
    @Column(name = "`sa_en_name`")
    @ApiModelProperty("英文名称")
    private String saEnName;

    /**
     * 简介
     */
    @Column(name = "`intro`")
    @ApiModelProperty("简介")
    private String intro;

    /**
     * 背景图片
     */
    @Column(name = "`sa_bg_img`")
    @ApiModelProperty("背景图片")
    private String saBgImg;

    /**
     * 背景视频
     */
    @Column(name = "`sa_bg_audio`")
    @ApiModelProperty("背景视频")
    private String saBgAudio;

    /**
     * 校训
     */
    @Column(name = "`school_motto`")
    @ApiModelProperty("校训")
    private String schoolMotto;

    /**
     * 地址
     */
    @Column(name = "`adress`")
    @ApiModelProperty("地址")
    private String adress;

    /**
     * 校区所在省
     */
    @Column(name = "`sa_province`")
    @ApiModelProperty("地址")
    private String saProvince;

    /**
     * 校区所在市
     */
    @Column(name = "`sa_city`")
    @ApiModelProperty("地址")
    private String saCity;

    /**
     * 校区所在区县
     */
    @Column(name = "`sa_county`")
    @ApiModelProperty("地址")
    private String saCounty;

    /**
     * 创办时间
     */
    @Column(name = "`found`")
    @ApiModelProperty("创办时间")
    private String found;

    /**
     * 办学性质
     */
    @Column(name = "`sch_property`")
    @ApiModelProperty("办学性质")
    private String schProperty;

    /**
     * 院系数量
     */
    @Column(name = "`academy_cnt`")
    @ApiModelProperty("院系数量")
    private String academyCnt;

    /**
     * 专业数量
     */
    @Column(name = "`major_cnt`")
    @ApiModelProperty("专业数量")
    private String majorCnt;

    /**
     *统计该学校的专业数量
     */
    @Transient
    @ApiModelProperty("统计专业数量")
    private String majorCount;

    /**
     * 在校人数
     */
    @Column(name = "`student_cnt`")
    @ApiModelProperty("在校人数")
    private String studentCnt;

    /**
     * 校园面积
     */
    @Column(name = "`acreage`")
    @ApiModelProperty("校园面积")
    private String acreage;

    /**
     * 硕士点
     */
    @Column(name = "`mater_pnt`")
    @ApiModelProperty("硕士点")
    private String materPnt;

    /**
     * 博士点
     */
    @Column(name = "`doctor_pnt`")
    @ApiModelProperty("博士点")
    private String doctorPnt;

    /**
     * 男女比例
     */
    @Column(name = "`sex_ratio`")
    @ApiModelProperty("男女比例")
    private String sexRatio;

    /**
     * 简介音频
     */
    @Column(name = "`intro_voice`")
    @ApiModelProperty("简介音频")
    private String introVoice;

    /**
     * 校区类型：1-本部（主校区）；2-分校区
     */
    @Column(name = "`sa_type`")
    @ApiModelProperty("校区类型：1-本部（主校区）；2-分校区")
    private Short saType;

    /**
     * 校区切换背景小图
     */
    @Column(name = "`sa_campus_img`")
    @ApiModelProperty("校区切换背景小图")
    private String saCompusImg;

    @Transient
    @Column(name = "unlock_status")
    @ApiModelProperty(value = "用户是否解锁 默认0未解锁， 1解锁", required = true)
    private Integer unlockStatus = 0;


    /**
     * 地图ID
     */
    @Column(name = "`mp_id`")
    @ApiModelProperty("地图ID")
    private Integer mpId;


    /**
     * 省份地址
     */
    @Transient
    @ApiModelProperty("省份地址")
    private String provinceAddress;

    /**
     * 校友信息
     */
    @Transient
    @ApiModelProperty("校友信息")
    private List<StSchAlumni> alumni;

    /**
     * 校园风光
     */
    @Transient
    @ApiModelProperty("校园风光")
    private List<StSchScenery> scenery;

    /**
     * 优势专业(写死的)
     */
    @Transient
    @ApiModelProperty("优势专业")
    private List<StSchMajorVo> advSpe;
//    private List<StSchMajor> advSpe;
    /**
     * 完整地址
     */
    @ApiModelProperty("完整地址")
    @Transient
    private String stAddres;
    /**
     * 所在城市
     */
    @ApiModelProperty("所在城市")
    @Transient
    private String stCity;

    public static final String SA_ID = "saId";

    public static final String DB_SA_ID = "sa_id";

    public static final String SCH_ID = "schId";

    public static final String DB_SCH_ID = "sch_id";

    public static final String LOGO = "logo";

    public static final String DB_LOGO = "logo";

    public static final String SA_NAME = "saName";

    public static final String DB_SA_NAME = "sa_name";

    public static final String SCH_NAME = "schName";

    public static final String DB_SCH_NAME = "sch_name";

    public static final String SCH_ICON = "schIcon";

    public static final String DB_SCH_ICON = "sch_icon";

    public static final String SA_EN_NAME = "saEnName";

    public static final String DB_SA_EN_NAME = "sa_en_name";

    public static final String INTRO = "intro";

    public static final String DB_INTRO = "intro";

    public static final String SA_BG_IMG = "saBgImg";

    public static final String DB_SA_BG_IMG = "sa_bg_img";

    public static final String SA_BG_AUDIO = "saBgAudio";

    public static final String DB_SA_BG_AUDIO = "sa_bg_audio";

    public static final String SCHOOL_MOTTO = "schoolMotto";

    public static final String DB_SCHOOL_MOTTO = "school_motto";

    public static final String ADRESS = "adress";

    public static final String DB_ADRESS = "adress";

    public static final String FOUND = "found";

    public static final String DB_FOUND = "found";

    public static final String SCH_PROPERTY = "schProperty";

    public static final String DB_SCH_PROPERTY = "sch_property";

    public static final String ACADEMY_CNT = "academyCnt";

    public static final String DB_ACADEMY_CNT = "academy_cnt";

    public static final String MAJOR_CNT = "majorCnt";

    public static final String DB_MAJOR_CNT = "major_cnt";

    public static final String STUDENT_CNT = "studentCnt";

    public static final String DB_STUDENT_CNT = "student_cnt";

    public static final String ACREAGE = "acreage";

    public static final String DB_ACREAGE = "acreage";

    public static final String MATER_PNT = "materPnt";

    public static final String DB_MATER_PNT = "mater_pnt";

    public static final String DOCTOR_PNT = "doctorPnt";

    public static final String DB_DOCTOR_PNT = "doctor_pnt";

    public static final String SEX_RATIO = "sexRatio";

    public static final String DB_SEX_RATIO = "sex_ratio";

    public static final String INTRO_VOICE = "introVoice";

    public static final String DB_INTRO_VOICE = "intro_voice";

    public static final String SA_TYPE = "saType";

    public static final String DB_SA_TYPE = "sa_type";

    public static final String ALUMNI = "alumni";

    public static final String SCENERY = "scenery";

    public static final String ADVSPE = "advSpe";

    public static StSchArea defaultInstance() {
        StSchArea instance = new StSchArea();
        return instance;
    }


    public StSchArea() {
    }



    /**
     * 所属学校查询无结果，添加学校功能中的插入主校区
     * @param schId
     * @param logo
     * @param saName
     * @param saCounty
     */
    public StSchArea(Integer schId, String logo, String saName, String saCounty) {
        this.schId = schId;
        this.logo = logo;
        this.saName = saName;
        this.saCounty = saCounty;
        this.saType = 1;
    }
}