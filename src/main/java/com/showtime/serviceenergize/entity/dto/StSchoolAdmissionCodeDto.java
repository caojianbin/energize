package com.showtime.serviceenergize.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * StSchoolAdmissionCodeDto
 * </p>
 *
 * @author cjb
 * @since 17:39
 */
@Data
public class StSchoolAdmissionCodeDto{

    /**
     * 招生代码
     */
    @ApiModelProperty("招生代码")
    private String schAdmissionCode;

}
