package com.showtime.serviceenergize.service;

import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.fallback.StProcodeClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * StFeignClientUserService
 * </p>
 *
 * @author cjb
 * @since 9:30
 */
@FeignClient(value = "user-service",fallback = StProcodeClientFallback.class)
public interface StFeignClientUserService {

    /**
     * 获取地图标签展示规则
     *
     * @return
     */
    @GetMapping("/st/stMapLabelManage/getMapLabelTestListByLevel.do")
    ResponseJsonCode getMapLabelTestListByLevel();

    /**
     * 查询所有标签分类
     *
     * @return
     */
    @GetMapping("/st/stLabelClassify/getStLabelClassifyList.do")
    ResponseJsonCode getStLabelClassifyList();

    /**
     * 查询所有分类下的高校标签
     *
     * @param stLabelId 分类id
     * @param saId      学校id
     * @return
     */
    @GetMapping("/stLabelSet/getLabelList.do")
    ResponseJsonCode getLabelList(@RequestParam("stLabelId") Integer stLabelId, @RequestParam("saId") Integer saId);

    /**
     * 周内浏览过当前学校的用户
     *
     * @param schId 学校id
     * @return
     */
    @GetMapping("/st/stUserSchViewRecord/getListBySchId.do")
    ResponseJsonCode getListBySchId(@RequestParam("schId")Integer schId);

    /**
     * 根据标签id查询
     *
     * @param slId 标签id
     * @return
     */
    @GetMapping("/st/stSchoolLabel/getSchoolLabel.do")
    ResponseJsonCode getSchoolLabel(@RequestParam("slId")Integer slId);
}
