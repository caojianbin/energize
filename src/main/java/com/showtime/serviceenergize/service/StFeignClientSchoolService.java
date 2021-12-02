package com.showtime.serviceenergize.service;

import com.lly835.bestpay.rest.type.Post;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.StProcode;
import com.showtime.serviceenergize.entity.StSchAlumni;
import com.showtime.serviceenergize.fallback.StProcodeClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 书唐省市区代码表 服务类
 * </p>
 *
 * @author cjb
 * @since 2019-12-23
 */
@FeignClient(name = "school-service", fallback = StProcodeClientFallback.class)
public interface StFeignClientSchoolService {

    /**
     * @return
     * @throws
     * @description 获取所有省份
     * @Param
     * @Author jlp
     * @Date 2019/12/25 11:14
     */
    @GetMapping(value = "/stProcode/find/getProvinces")
    List<StProcode> getProvinces();

    /**
     * @return
     * @throws
     * @description 获取所有城市
     * @Param
     * @Author jlp
     * @Date 2019/12/25 11:14
     */
    @GetMapping(value = "/stProcode/find/getCitys")
    List<StProcode> getCitys();

    /**
     * @return
     * @throws
     * @description 获取所有区
     * @Param
     * @Author jlp
     * @Date 2019/12/25 11:15
     */
    @GetMapping(value = "/stProcode/find/getAreas")
    List<StProcode> getAreas();

    /**
     * 调用school服务的 selectAListOfCarousels 首页轮播图相关接口
     *
     * @param type 轮播图类型
     * @return
     */
    @GetMapping("/stSchCarouselMap/getAListOfCarousels")
    ResponseJsonCode selectAListOfCarousels(@RequestParam("type") Integer type);

    /**
     * 根据学校ID查询对应的知名校友的信息
     *
     * @param schoolId 学校ID
     * @return
     */
    @GetMapping("/st/school/alumni/selectAlumniFeign")
    List<StSchAlumni> selectAlumniFeign(@RequestParam("schoolId") int schoolId);


    /**
     * 根据学校ID查询专业信息
     *
     * @param schId 学校ID
     * @return
     */
    @GetMapping(value = "/st/school/major/getSpecialty")
    ResponseJsonCode selectAdvantageStSchCollegeList(@RequestParam("schId") Integer schId);


    /**
     * 根据学校ID查询所有校区名称列表
     *
     * @param schId 学校ID
     * @return
     */
    @GetMapping("/st/school/web/merge/getStCampusNameList")
    ResponseJsonCode selectStCampusNameLists(@RequestParam("schId") Integer schId);

    /**
     * 院校分数线
     *
     * @param schId    学校ID
     * @param province 省份（例子：510000）
     * @param subject  科目 1理科 2文科（例子：1）
     * @param year     年份
     * @return
     */
    @GetMapping("/st/schRecruitInfo/selectListCollegeScoreLine")
    ResponseJsonCode selectListCollegeScoreLine(@RequestParam("schId") Integer schId,
                                                @RequestParam("province") String province,
                                                @RequestParam("subject") String subject,
                                                @RequestParam("year") String year,
                                                @RequestParam("pageNum")String pageNum,
                                                @RequestParam("pageSize")String pageSize);

    /**
     * 批次下拉框
     *
     * @param schId 学校ID
     * @return
     */
    @GetMapping("/st/schDropDownBox/getBatch")
    ResponseJsonCode getBatch(@RequestParam("schId") Integer schId);

    /**
     * 省份下拉框
     *
     * @return
     */
    @GetMapping("/st/schDropDownBox/getProvince")
    ResponseJsonCode getProvince();

    /**
     * 年份下拉框
     *
     * @param schId 学校ID
     * @return
     */
    @GetMapping("/st/schDropDownBox/getYear")
    ResponseJsonCode getYear(@RequestParam("schId") Integer schId);

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
    @GetMapping("/st/schMajorRecruitInfo/professionalAdmissionScore")
    ResponseJsonCode selectPageSchProfessionalAdmissionScore(@RequestParam("schId") Integer schId,
                                                             @RequestParam("subject") Integer subject,
                                                             @RequestParam("batch") Integer batch,
                                                             @RequestParam("year") String year,
                                                             @RequestParam("pageNum") Integer pageNum,
                                                             @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据学校的ID查询学校简介的信息
     *
     * @param schId 学校ID
     * @return
     */
    @GetMapping("/st/school/detail/schId/getDetail")
    ResponseJsonCode selectAlumniBySchId(@RequestParam("schId") int schId);

    /**
     * 根据学校的ID分页查询招生政策
     *
     * @param schlId 学校ID
     * @return
     */
    @GetMapping("/st/school/recruit/pageInfo/getRecruitNews")
    ResponseJsonCode selectSchRecruitNewsPageInfoOrderByDate(@RequestParam("schlId") int schlId,
                                                             @RequestParam("pageNum") int pageNum,
                                                             @RequestParam("pageSize") int pageSize);

    /**
     * 根据招生政策的ID查询招生政策的信息
     *
     * @param rnId 招生政策的ID
     * @return
     */
    @GetMapping("/st/school/recruit/content/getRecruitNews")
    ResponseJsonCode selectSchRecruitNewsContent(@RequestParam("rnId") int rnId);

    /**
     * 根据校区ID查询对应的校区联系信息
     *
     * @param saId 校区ID
     * @return
     */
    @GetMapping("/st/school/contactInfo/getContactInfo")
    ResponseJsonCode selectAlumni(@RequestParam("saId") int saId);

    /**
     * 查询院校金额
     * @param
     * @return
     */
    @PostMapping("/stSchoolPrice/selectSchoolPrice")
    ResponseJsonCode selectSchoolPriceList(@RequestParam("schId")Integer schId);
}
