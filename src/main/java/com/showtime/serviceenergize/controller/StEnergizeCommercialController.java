package com.showtime.serviceenergize.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.annotation.SystemControllerLog;
import com.showtime.serviceenergize.entity.vo.StEnergizeCommercialVo;
import com.showtime.serviceenergize.service.StEnergizeCommercialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 书唐赋能商户管理表 前端控制器
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
@RestController
@RequestMapping("st/stEnergizeCommercial")
@Api(tags = "书唐赋能商户管理")
public class StEnergizeCommercialController {
    /**
     * 书唐赋能商户管理 服务
     */
    @Autowired
    private StEnergizeCommercialService commercialService;

    /**
     * 根据条件全部查询
     *
     * @param ctName 商户名称
     * @param page   分页信息
     * @return 商户集合
     */
    @SystemControllerLog(description = "查询商户")
    @GetMapping("selctCommercialList.do")
    @ApiOperation(value = "根据条件全部查询",notes = "分页查询返回对象[json],作者：卑微的小曹")
    @ApiImplicitParam(paramType = "query",name = "ctName", value = "商户名称", required = false, dataType = "String")
    public ResponseJsonCode selctCommercialList(Page page, String ctName){
       return ResponseJsonCode.successRequestJsonCode(commercialService.selctCommercialList(page,ctName));
    }

    /**
     * 添加赋能商户
     *
     * @param  commercialVo 商户信息
     * @return 成功条件
     */
    @SystemControllerLog(description = "添加赋能商户")
    @PostMapping("addEnergize.do")
    @ApiOperation(value = "添加赋能商户",notes = "添加商户对象,作者：卑微的小曹")
    public ResponseJsonCode addEnergize(StEnergizeCommercialVo commercialVo){
        Integer addRetNum = commercialService.addEnergize(commercialVo);
        return (addRetNum == 1)?ResponseJsonCode.cusResponseJsonCode(200,"添加成功"):ResponseJsonCode.cusResponseJsonCode(500,"添加失败");
    }

    /**
     * 修改赋能商户
     *
     * @param commercialVo 商户信息
     * @param ctId 商户id
     * @return 成功条件
     */
    @SystemControllerLog(description = "修改赋能商户")
    @PostMapping("updateEnergize.do")
    @ApiOperation(value = "修改赋能商户",notes = "修改赋能商户,作者：卑微的小曹")
    @ApiImplicitParam(name = "ctId",value = "商户id",required = true)
    public ResponseJsonCode updateEnergize(String ctId, StEnergizeCommercialVo commercialVo){
        Integer updateRestNum = commercialService.updateEnergize(ctId, commercialVo);
        return (updateRestNum == 1)?ResponseJsonCode.cusResponseJsonCode(200,"修改成功"):ResponseJsonCode.cusResponseJsonCode(500,"修改失败");
    }

    /**
     * 根据用户id修改状态
     *
     * @param ctId 商户id
     * @return 成功条件
     */
    @SystemControllerLog(description = "根据id修改用户状态商户")
    @PostMapping("delEnergize.do")
    @ApiOperation(value = "根据id修改用户状态商户",notes = "删除商户,作者：卑微的小曹")
    public ResponseJsonCode delEnergize(String ctId,Integer ctStatus){
        Integer delRetNum = commercialService.delEnergize(ctId,ctStatus);
        return (delRetNum == 1)?ResponseJsonCode.cusResponseJsonCode(200,"状态修改成功"):ResponseJsonCode.cusResponseJsonCode(500,"状态修改失败");
    }

}

