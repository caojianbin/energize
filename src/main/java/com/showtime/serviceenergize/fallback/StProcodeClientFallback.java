package com.showtime.serviceenergize.fallback;

import com.showtime.common.exception.MyException;
import com.showtime.common.model.ResultEnum;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.StProcode;
import com.showtime.serviceenergize.entity.StSchAlumni;
import com.showtime.serviceenergize.service.StFeignClientSchoolService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 业务层针对school地区服务做异常处理
 */
@Component
public class StProcodeClientFallback implements StFeignClientSchoolService {

    @Override
    public List<StProcode> getProvinces() {
        throw new MyException(ResultEnum.ST_SELECT_NULL);
    }

    @Override
    public List<StProcode> getCitys() {
        throw new MyException(ResultEnum.ST_SELECT_NULL);
    }

    @Override
    public List<StProcode> getAreas() {
        throw new MyException(ResultEnum.ST_SELECT_NULL);
    }

    @Override
    public ResponseJsonCode selectAListOfCarousels(Integer type) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public List<StSchAlumni> selectAlumniFeign(int schoolId) {
        throw new MyException(ResultEnum.ST_SELECT_NULL);
    }

    @Override
    public ResponseJsonCode selectAdvantageStSchCollegeList(Integer schId) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode selectStCampusNameLists(Integer schId) {
        return null;
    }

    @Override
    public ResponseJsonCode selectListCollegeScoreLine(Integer schId, String province, String subject, String year,String pageNum,String pageSize ) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode getBatch(Integer schId) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode getProvince() {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode getYear(Integer schId) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode selectPageSchProfessionalAdmissionScore(Integer schId, Integer subject, Integer batch, String year, Integer pageNum, Integer pageSize) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode selectAlumniBySchId(int schId) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode selectSchRecruitNewsPageInfoOrderByDate(int schlId, int pageNum, int pageSize) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode selectSchRecruitNewsContent(int rnId) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode selectAlumni(int saId) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }

    @Override
    public ResponseJsonCode selectSchoolPriceList(Integer schId) {
        return ResponseJsonCode.cusResponseJsonCode(ResultEnum.ST_SELECT_NULL.getCode(),ResultEnum.ST_SELECT_NULL.getMsg());
    }
}
