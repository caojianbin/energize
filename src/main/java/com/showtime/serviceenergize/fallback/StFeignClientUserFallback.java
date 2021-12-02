package com.showtime.serviceenergize.fallback;

import com.showtime.common.model.ResultEnum;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.service.StFeignClientUserService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * StFeignClientUserFallback
 * </p>
 *
 * @author cjb
 * @since 16:08
 */
@Component
public class StFeignClientUserFallback implements StFeignClientUserService {
    @Override
    public ResponseJsonCode getMapLabelTestListByLevel() {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode getStLabelClassifyList() {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode getLabelList(Integer stLabelId, Integer saId) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode getListBySchId(Integer schId) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode getSchoolLabel(Integer slId) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }
}
