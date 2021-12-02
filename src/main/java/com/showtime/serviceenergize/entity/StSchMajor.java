package com.showtime.serviceenergize.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@JsonInclude(value= JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@ApiModel("")
@Table(name = "`st_sch_major`")
public class StSchMajor {
    /**
     * 主键
     */
    @Id
    @Column(name = "`sm_id`")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("主键")
    private Integer smId;

    /**
     * 所属院系id
     */
    @Column(name = "`sc_id`")
    @ApiModelProperty("所属院系id")
    private Integer scId;

    /**
     * 专业id
     */
    @Column(name = "`em_id`")
    @ApiModelProperty("专业id")
    private Integer emId;

    /**
     * 是否优势专业：0-否；1-是
     */
    @Column(name = "`is_advantage`")
    @ApiModelProperty("是否优势专业：0-否；1-是")
    private Short isAdvantage;

    /**
     * 显示顺序
     */
    @Column(name = "`sm_order`")
    @ApiModelProperty("显示顺序")
    private Integer smOrder;

    /**
     * 专业等级
     */
    @Column(name = "`sm_grade`")
    @ApiModelProperty("专业等级")
    private String smGrade;

    /**
     * 专业等级
     */
    @Column(name = "`sm_name`")
    @ApiModelProperty("专业名称")
    private String smName;

    /**
     * 专业等级
     */
    @Column(name = "`sm_code`")
    @ApiModelProperty("专业代码")
    private String smCode;

    /**
     * 专业等级
     */
    @Column(name = "`sm_detail`")
    @ApiModelProperty("专业详情")
    private String smDetail;

    /**
     *
     */
    @Transient
    @ApiModelProperty("专业信息")
    private StExamMajor stExamMajor;

    public static final String SM_ID = "smId";

    public static final String DB_SM_ID = "sm_id";

    public static final String SC_ID = "scId";

    public static final String DB_SC_ID = "sc_id";

    public static final String EM_ID = "emId";

    public static final String DB_EM_ID = "em_id";

    public static final String IS_ADVANTAGE = "isAdvantage";

    public static final String DB_IS_ADVANTAGE = "is_advantage";

    public static final String SM_ORDER = "smOrder";

    public static final String DB_SM_ORDER = "sm_order";

    public static final String SM_GRADE = "smGrade";

    public static final String DB_SM_GRADE = "sm_grade";

    public static StSchMajor defaultInstance() {
        StSchMajor instance = new StSchMajor();
        return instance;
    }
}