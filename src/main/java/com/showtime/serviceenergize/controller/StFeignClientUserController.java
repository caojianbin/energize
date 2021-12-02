package com.showtime.serviceenergize.controller;

import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.service.StFeignClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * StFeignClientUserController
 * </p>
 *
 * @author cjb
 * @since 9:31
 */
@RestController
@RequestMapping("fn/user/FeignClient")
@Api(tags = "(书唐赋能H5)-书唐地图标签相关接口")
public class StFeignClientUserController {

    @Autowired
    private StFeignClientUserService userService;

    /**
     * 获取地图标签展示规则
     *
     * @return
     */
    @GetMapping("getMapLabelTestListByLevel.do")
    @ApiOperation(value = "(赋能H5地图)-地图所有层级标签呈现方式", notes = "(赋能H5地图)-地图所有层级标签呈现方式")
    public ResponseJsonCode getMapLabelTestListByLevel() {
        return userService.getMapLabelTestListByLevel();
    }

    /**
     * 查询所有标签分类
     *
     * @return
     */
    @GetMapping("getStLabelClassifyList.do")
    @ApiOperation(value = "(赋能H5地图)-查询所有标签分类", notes = "(赋能H5地图)-查询所有标签分类")
    public ResponseJsonCode getStLabelClassifyList() {
        return userService.getStLabelClassifyList();
    }

    /**
     * 查询所有分类下的高校标签
     *
     * @param stLabelId 分类id
     * @param saId      学校id
     * @return
     */
    @GetMapping("getLabelList.do")
    @ApiOperation(value = "(赋能H5地图)-查询所有分类下面的标签",notes = "(赋能H5地图)-查询所有分类下面的标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stLabelId", value = "大类id（物理构建id）", paramType = "query", defaultValue = "1",required = true, dataType = "int"),
            @ApiImplicitParam(name = "saId", value = "校区id", paramType = "query", required = true, defaultValue = "4901",dataType = "int")})
    public ResponseJsonCode getLabelList(Integer stLabelId, Integer saId) {
        return userService.getLabelList(stLabelId,saId);
    }

    /**
     * 周内浏览过当前学校的用户
     *
     * @param schId 学校id
     * @return
     */
    @GetMapping("getListBySchId.do")
    @ApiOperation(value = "(赋能H5解锁页面)-周内浏览过当前学校的用户", notes = "(赋能H5解锁页面)-周内浏览过当前学校的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schId", value = "学校id",defaultValue = "49", paramType = "query", required = true, dataType = "int")})
    public ResponseJsonCode getListBySchId(Integer schId){
        return userService.getListBySchId(schId);
    }

    /**
     * 根据标签id查询
     *
     * @param slId 标签id
     * @return
     */
    @GetMapping("getSchoolLabel.do")
    @ApiOperation(value = "(赋能H5地图)根据标签id查询学校标签",notes = "(赋能H5地图)根据标签id查询学校标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "slId", value = "标签id",defaultValue = "49", paramType = "query", required = true, dataType = "int")})
    public ResponseJsonCode getSchoolLabel(Integer slId){
        return userService.getSchoolLabel(slId);
    }
}
