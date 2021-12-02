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
@Table(name = "`st_sch_scenery`")
public class StSchScenery implements Serializable {
    private static final long serialVersionUID = -1407767548861759009L;
    /**
     * 主键
     */
    @Id
    @Column(name = "`s_id`")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("主键")
    private Integer sId;

    /**
     * 所属学校
     */
    @Column(name = "`sch_id`")
    @ApiModelProperty("所属学校")
    private Integer schId;

    /**
     * 所属校区
     */
    @Column(name = "`ar_id`")
    @ApiModelProperty("所属校区")
    private Integer arId;

    /**
     * 图片名称
     */
    @Column(name = "`name`")
    @ApiModelProperty("图片名称")
    private String name;

    /**
     * 简介
     */
    @Column(name = "`intro`")
    @ApiModelProperty("简介")
    private String intro;

    /**
     * 风光图
     */
    @Column(name = "`picture`")
    @ApiModelProperty("风光图")
    private String picture;

    public static final String A_ID = "sId";

    public static final String DB_A_ID = "s_id";

    public static final String SCH_ID = "schId";

    public static final String DB_SCH_ID = "sch_id";

    public static final String AR_ID = "arId";

    public static final String DB_AR_ID = "ar_id";

    public static final String NAME = "name";

    public static final String DB_NAME = "name";

    public static final String INTRO = "intro";

    public static final String DB_INTRO = "intro";

    public static final String PICTURE = "picture";

    public static final String DB_PICTURE = "picture";

    public static StSchScenery defaultInstance() {
        StSchScenery instance = new StSchScenery();
        return instance;
    }
}