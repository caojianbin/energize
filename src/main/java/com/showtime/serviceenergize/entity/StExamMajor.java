package com.showtime.serviceenergize.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@JsonInclude(value= JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@ApiModel("")
@Table(name = "`st_exam_major`")
public class StExamMajor {
    /**
     * 主键
     */
    @Id
    @Column(name = "`em_id`")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("主键")
    private Integer emId;

    /**
     * 专业代码
     */
    @Column(name = "`major_code`")
    @ApiModelProperty("专业代码")
    private String majorCode;

    /**
     * 专业名称
     */
    @Column(name = "`major_name`")
    @ApiModelProperty("专业名称")
    private String majorName;

    /**
     * 专业父级代码：根级为0；
     */
    @Column(name = "`parent_code`")
    @ApiModelProperty("专业父级代码：根级为0；")
    private String parentCode;

    /**
     * 相近专业代码
     */
    @Column(name = "`near_major`")
    @ApiModelProperty("相近专业代码")
    private String nearMajor;

    public static final String EM_ID = "emId";

    public static final String DB_EM_ID = "em_id";

    public static final String MAJOR_CODE = "majorCode";

    public static final String DB_MAJOR_CODE = "major_code";

    public static final String MAJOR_NAME = "majorName";

    public static final String DB_MAJOR_NAME = "major_name";

    public static final String PARENT_CODE = "parentCode";

    public static final String DB_PARENT_CODE = "parent_code";

    public static final String NEAR_MAJOR = "nearMajor";

    public static final String DB_NEAR_MAJOR = "near_major";

    public static StExamMajor defaultInstance() {
        StExamMajor instance = new StExamMajor();
        return instance;
    }
}