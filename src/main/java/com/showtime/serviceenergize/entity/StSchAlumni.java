package com.showtime.serviceenergize.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel("")
@Table(name = "`st_sch_alumni`")
public class StSchAlumni implements Serializable {
    private static final long serialVersionUID = -1407767548861759009L;
    /**
     * 主键
     */
    @Id
    @Column(name = "`a_id`")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("主键")
    private Integer aId;

    /**
     * 所属学校
     */
    @Column(name = "`sch_id`")
    @ApiModelProperty("所属学校")
    private Integer schId;

    /**
     * 姓名
     */
    @Column(name = "`name`")
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 称号
     */
    @Column(name = "`designation`")
    @ApiModelProperty("称号")
    private String designation;

    /**
     * 简介
     */
    @Column(name = "`intro`")
    @ApiModelProperty("简介")
    private String intro;

    /**
     * 音频简介
     */
    @Column(name = "`intro_voice`")
    @ApiModelProperty("音频简介")
    private String introVoice;

    /**
     * 照片
     */
    @Column(name = "`picture`")
    @ApiModelProperty("照片")
    private String picture;

    public static final String A_ID = "aId";

    public static final String DB_A_ID = "a_id";

    public static final String SCH_ID = "schId";

    public static final String DB_SCH_ID = "sch_id";

    public static final String NAME = "name";

    public static final String DB_NAME = "name";

    public static final String INTRO = "intro";

    public static final String DB_INTRO = "intro";

    public static final String INTRO_VOICE = "introVoice";

    public static final String DB_INTRO_VOICE = "intro_voice";

    public static final String PICTURE = "picture";

    public static final String DB_PICTURE = "picture";

    public static final String DESIGNATION = "designation";

    public static StSchAlumni defaultInstance() {
        StSchAlumni instance = new StSchAlumni();
        return instance;
    }
}