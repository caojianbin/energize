package com.showtime.serviceenergize.controller;

import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.StSchArea;
import com.showtime.serviceenergize.service.StSchAreaDetailMergeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cjb
 *
 * @Date 2020/1/7 0007
 *
 * @Description //TODO
 */
@Api(tags = "(书唐赋能)-未激活学校详情页-（*有修改2020-1.14）")
@RestController
@RequestMapping("/st/school/web/merge")
@Slf4j
public class StSchAreaDetailMergeController {
    @Autowired
    private StSchAreaDetailMergeService stSchAreaDetailMergeService;


    /**
     * 根据学校ID和用户ID查询学校详情页
     *
     * @param schId                学校id
     * @param userNumber           商户用户号
     * @param ctIdentificationCode 商户号
     * @return
     */
    @ApiOperation(value = "查询学校详情页",notes = "查询学校详情页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schId",value = "学校的ID",paramType = "query",defaultValue = "49",required = true,dataType = "int"),
            @ApiImplicitParam(name = "userNumber",value = "商户编号",paramType = "query",defaultValue = "454545",required = true,dataType = "String"),
            @ApiImplicitParam(name = "ctIdentificationCode",value = "商户用户账号（*修改字段）",paramType = "query",defaultValue = "stsh_IZJ4thHy",required = true,dataType = "String")
    })
    @GetMapping(value = "/getAreaDetailBySchId")
    public ResponseJsonCode selectStSchAreaDetail( Integer schId ,String userNumber, String ctIdentificationCode ){
        log.info("要查询学校的ID{}",schId);
        //获取数据
        StSchArea stSchArea = stSchAreaDetailMergeService.selectStSchAreaDetail( schId , userNumber,ctIdentificationCode);
        return ResponseJsonCode.successRequestJsonCode(stSchArea);

    }

}
