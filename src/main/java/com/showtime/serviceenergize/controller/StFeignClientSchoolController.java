package com.showtime.serviceenergize.controller;

import com.github.pagehelper.PageInfo;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.StProcode;
import com.showtime.serviceenergize.entity.StSchAlumni;
import com.showtime.serviceenergize.fallback.StProcodeClientFallback;
import com.showtime.serviceenergize.service.StFeignClientSchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 书唐省市区代码表 服务类
 * </p>
 *
 * @author cjb
 * @since 2019-12-23
 */
@RestController
@RequestMapping("/fn/school/FeignClient")
@Api(tags = "(书唐赋能H5)-书唐录的学校专业信息相关的接口")
public class StFeignClientSchoolController {

    @Autowired
    private StFeignClientSchoolService clientSchoolService;

    /**
     * 根据学校ID查询专业信息
     *
     * @param schId
     * @return
     */
    @ApiOperation(value = "(书唐赋能)-根据学校ID查询专业信息", notes = "(书唐赋能)-根据学校ID查询专业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schId", value = "学校ID", paramType = "query", required = true, dataType = "int")})
    @GetMapping(value = "/getSpecialty")
    public ResponseJsonCode selectAdvantageStSchCollegeList(Integer schId) {
        return clientSchoolService.selectAdvantageStSchCollegeList(schId);
    }

    /**
     * 根据学校ID查询所有校区名称列表
     *
     * @param schId
     * @return
     */
    @ApiOperation(value = "(书唐赋能)-根据学校ID查询所有校区名称列表", notes = "(书唐赋能)-根据学校ID查询所有校区名称列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schId", value = "学校的ID", paramType = "query", required = true, dataType = "int")
    })
    @GetMapping(value = "/getStCampusNameList")
    public ResponseJsonCode selectStCampusNameLists(Integer schId) {
        return clientSchoolService.selectStCampusNameLists(schId);

    }

    /**
     * 院校分数线
     *
     * @param schId    学校ID
     * @param province 省份（例子：510000）
     * @param subject  科目 1理科 2文科（例子：1）
     * @param year     年份
     * @return
     */
    @ApiOperation(value = "院校分数线", notes = "院校分数线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schId", value = "学校ID", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "province", value = "省份（例子：510000）", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "subject", value = "科目 1理科 2文科（例子：1）", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "year", value = "年份（例子：2018） 如果为空则默认给你最新年份的数据", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageNum",value = "当前页",paramType = "query",required = false,dataType = "String"),
            @ApiImplicitParam(name = "pageSize",value = "每页条数",paramType = "query",required = false,dataType = "String")})
    @GetMapping(value = "/selectListCollegeScoreLine")
    public ResponseJsonCode selectListCollegeScoreLine(Integer schId, String province, String subject, String year,String pageNum,String pageSize) {
        return clientSchoolService.selectListCollegeScoreLine(schId, province, subject, year,pageNum,pageSize);
    }

    /**
     * 批次下拉框
     *
     * @param schId 学校ID
     * @return
     */
    @ApiOperation(value = "批次下拉框", notes = "批次下拉框")
    @ApiImplicitParam(name = "schId", value = "学校ID（例子：456）", paramType = "query", required = true, dataType = "int")
    @GetMapping(value = "/getBatch")
    public ResponseJsonCode getBatch(Integer schId) {
        return clientSchoolService.getBatch(schId);
    }

    /**
     * 省份下拉框
     *
     * @return
     */
    @ApiOperation(value = "省份下拉框", notes = "省份下拉框")
    @GetMapping(value = "/getProvince")
    public ResponseJsonCode getProvince() {
        return clientSchoolService.getProvince();
    }

    @ApiOperation(value = "年份下拉框",notes = "年份下拉框")
    @ApiImplicitParam(name = "schId",value = "学校ID（例子：456）",paramType = "query",required = true,dataType = "int")
    @GetMapping(value = "/getYear")
    public ResponseJsonCode getYear(Integer schId){
        return clientSchoolService.getYear(schId);
    }

    /**
     * 专业录取分数线
     *
     * @param schId    学校ID
     * @param subject  批次 1一本  2二本  3专科  4高职  5其他
     * @param batch    科目 1理科 2文科（例子：1）
     * @param year     年份
     * @param pageNum  当前页数
     * @param pageSize 当前页需要显示的数量
     * @return
     */
    @ApiOperation(value = "专业录取分数线", notes = "专业录取分数线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schId", value = "学校ID (例子：1206)", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "batch", value = "批次 1一本  2二本  3专科  4高职  5其他", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "subject", value = "科目 1理科 2文科（例子：1）", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "year", value = "年份（例子：2017）如果为空则默认给你最新年份的数据", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "当前页数（例子：1）", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "当前页需要显示的数量（例子：10）", paramType = "query", required = true, dataType = "int")})
    @GetMapping(value = "/professionalAdmissionScore")
    public ResponseJsonCode selectPageSchProfessionalAdmissionScore(Integer schId, Integer subject, Integer batch, String year, Integer pageNum, Integer pageSize) {
        return clientSchoolService.selectPageSchProfessionalAdmissionScore(schId, subject, batch, year, pageNum, pageSize);
    }

    /**
     * 根据学校ID查询
     *
     * @param schId
     * @return
     */
    @ApiOperation(value = "根据学校的ID查询院校简介的信息", notes = "查询数据库中某个院校简介的信息")
    @ApiImplicitParam(name = "schId", value = "学校的ID", paramType = "query", required = true, dataType = "int")
    @GetMapping(value = "/schId/getDetail")
    public ResponseJsonCode selectAlumniBySchId(int schId) {
        return clientSchoolService.selectAlumniBySchId(schId);
    }

    /**
     * 根据学校的ID分页查询招生政策
     *
     * @param schlId
     * @return
     */
    @ApiOperation(value = "根据学校的ID分页查询招生政策的信息", notes = "分页查询数据库中某个招生政策的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schlId", value = "学校的ID", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "当前页数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "当前页需要显示的数量", paramType = "query", required = true, dataType = "int")})
    @GetMapping(value = "/pageInfo/getRecruitNews")
    public ResponseJsonCode selectSchRecruitNewsPageInfoOrderByDate( int schlId, int pageNum, int pageSize) {
        return clientSchoolService.selectSchRecruitNewsPageInfoOrderByDate(schlId, pageNum, pageSize);
    }

    /**
     * 根据招生政策的ID查询招生政策的信息
     *
     * @param rnId
     * @return
     */
    @ApiOperation(value = "根据招生政策的ID查询招生政策的信息", notes = "查询数据库中某个招生政策的信息")
    @ApiImplicitParam(name = "rnId", value = "招生政策的ID", paramType = "query", required = true, dataType = "int")
    @GetMapping(value = "/content/getRecruitNews")
    public ResponseJsonCode selectSchRecruitNewsContent( int rnId) {
        return clientSchoolService.selectSchRecruitNewsContent(rnId);
    }

    /**
     * 根据校区ID查询对应的校区联系信息
     *
     * @param saId 校区ID
     * @return
     */
    @ApiOperation(value = "根据校区的ID查询校区联系信息", notes = "查询数据库中某个校区的联系信息")
    @ApiImplicitParam(name = "saId", value = "校区的ID", paramType = "query", required = true, dataType = "int")
    @GetMapping(value = "/getContactInfo")
    public ResponseJsonCode selectAlumni( int saId) {
        return clientSchoolService.selectAlumni(saId);
    }

    /**
     * 查询院校金额
     * @param
     * @return
     */
    @ApiOperation(value = "(查询院校金额",notes = "查询院校金额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schId",value = "学校ID",paramType = "query",required = true,dataType = "int")})
    @PostMapping(value = "/selectSchoolPrice")
    public ResponseJsonCode selectSchoolPriceList(Integer schId){
        return clientSchoolService.selectSchoolPriceList(schId);
    }
}
