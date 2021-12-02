package com.showtime.serviceenergize.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author xrj
 * @Date 2019/2/14 15:43
 * @Description //TODO
 */
@Data
@Accessors(chain = true)
@ApiModel("")
@Table(name = "`st_sch_tag_info`")
public class StSchTagInfo {
    /**
     * 主键
     */
    @Id
    @Column(name = "`t_id`")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("主键")
    private Integer tId;

    /**
     * 标签名称
     */
    @Column(name = "`tag_name`")
    @ApiModelProperty(value = "标签名称",example = "0")
    private String tagName;

    /**
     * 高校id
     */
    @Column(name = "`sch_id`")
    @ApiModelProperty("高校id")
    private Integer schId;

    /**
     * 排序
     */
    @Column(name = "`t_order`")
    @ApiModelProperty("排序")
    private Integer tOrder;

    public static final String T_ID = "tId";

    public static final String DB_T_ID = "t_id";

    public static final String TAG_NAME = "tagName";

    public static final String DB_TAG_NAME = "tag_name";

    public static final String SCH_ID = "schId";

    public static final String DB_SCH_ID = "sch_id";

    public static final String T_ORDER = "tOrder";

    public static final String DB_T_ORDER = "t_order";

    public static StSchTagInfo defaultInstance() {
        StSchTagInfo instance = new StSchTagInfo();
        return instance;
    }
}