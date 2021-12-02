package com.showtime.serviceenergize.controller;


import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.entity.StSchSchool;
import com.showtime.serviceenergize.entity.dto.HomeSchoolDto;
import com.showtime.serviceenergize.entity.dto.StSchoolAdmissionCodeDto;
import com.showtime.serviceenergize.entity.vo.FuzzyQuerySchoolVo;
import com.showtime.serviceenergize.service.StFeignClientSchoolService;
import com.showtime.serviceenergize.service.StSchSchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 学校信息表 前端控制器
 * </p>
 *
 * @author cjb
 * @since 2019-12-26
 */
@RestController
@RequestMapping("st/stSchSchool")
@Api(tags = "(赋能首页)学校相关接口（*有修改2020-1.14）")
public class StSchSchoolController {

    /**
     * 查询学校
     */
    @Autowired
    private StSchSchoolService schoolService;
    /**
     * 首页轮播图
     */
    @Autowired
    private StFeignClientSchoolService procodeService;

    @ApiOperation(value = "(赋能第二个方案)-根据学校编码查询学校-（*有修改）", notes = "(赋能第二个方案)-根据学校编码查询学校-（*有修改）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schAdmissionCode", value = "学校编码", required = true, paramType = "query", defaultValue = "10001",dataType = "String"),
            @ApiImplicitParam(name = "userNumber", value = "商户用户账号", required = true, paramType = "query", defaultValue = "454545",dataType = "String"),
            @ApiImplicitParam(name = "ctIdentificationCode", value = "商户号(*修改字段，之前字段“ctId”)", required = true, paramType = "query", defaultValue = "stsh_IZJ4thHy",dataType = "String")

    })
    @GetMapping("getSchoolByCode.do")
    public ResponseJsonCode getSchoolByName(String schAdmissionCode,String userNumber, String ctIdentificationCode) {
        return schoolService.getSchoolByCoding(schAdmissionCode,userNumber,ctIdentificationCode);
    }

    /**
     * 分页查询学校列表信息
     *
     * @return
     */
    @GetMapping("getSchSchoolList.do")
    @ApiOperation(value = "(赋能首页)-学校列表信息-（*有修改）", notes = "(赋能首页)-学校列表信息-（*有修改）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userNumber", value = "用户账号(与是否解锁相关)", paramType = "query", defaultValue = "454545", required = true, dataType = "int"),
            @ApiImplicitParam(name = "ctIdentificationCode", value = "商户号(*修改字段，之前字段“ctId”)", paramType = "query", defaultValue = "stsh_IZJ4thHy", required = true, dataType = "int"),
            @ApiImplicitParam(name = "isPlat", value = "是否可视化  1:是  0:否", paramType = "query", defaultValue = "1", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "当前页数（例子：1）", paramType = "query", required = true, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "当前页需要显示的数量（例子：10）", paramType = "query", defaultValue = "10", required = true, dataType = "int")
    })
    public ResponseJsonCode getSchSchoolList(String userNumber, String ctIdentificationCode, Integer pageNum, Integer pageSize, Integer isPlat) {
        return ResponseJsonCode.successRequestMsg("查询成功", schoolService.getSchSchoolList(userNumber, ctIdentificationCode, pageNum, pageSize, isPlat));
    }


    /**
     * 查询轮播图列表
     *
     * @return
     */
    @ApiOperation(value = "(赋能首页)-轮播图", notes = "(赋能3首页)-轮播图")
    @ApiImplicitParam(name = "type", value = "轮播图类型（1-首页，2-个人中心，3-千校可视，轮播图）", defaultValue = "1", paramType = "query", required = true, dataType = "int")
    @GetMapping(value = "/getAListOfCarousels")
    public ResponseJsonCode selectBanner( Integer type) {
        return procodeService.selectAListOfCarousels(type);
    }

    @ApiOperation(value = "（赋能首页搜索框）查询学校名称列表-（*有修改）",notes = "（赋能首页搜索框）查询学校名称列表-（*有修改）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schName",value = "学校名字(例子：qingh",paramType = "query",defaultValue = "四川",required = false,dataType = "String"),
            @ApiImplicitParam(name = "userNumber",value = "用户账号",paramType = "query",defaultValue = "454545",required = true,dataType = "int"),
            @ApiImplicitParam(name = "ctIdentificationCode",value = "商户号(*修改字段，之前字段“ctId”)",paramType = "query",defaultValue = "stsh_IZJ4thHy",required = true,dataType = "int"),
            @ApiImplicitParam(name = "pageNum",value = "当前页数（例子：1）",defaultValue = "1",paramType = "query",required = true,dataType = "int"),
            @ApiImplicitParam(name = "pageSize",value = "当前页需要显示的数量（例子：10）",defaultValue = "10",paramType = "query",required = true,dataType = "int")})
    @PostMapping(value = "/pageInfo/bySchName")
    public ResponseJsonCode schName(String schName, String userNumber, String ctIdentificationCode,Integer pageNum,  Integer pageSize){
        List<FuzzyQuerySchoolVo> stSchSchoolList= schoolService.selectStSchoolNameListBySchName(schName,null,userNumber,ctIdentificationCode, pageNum, pageSize);
        return ResponseJsonCode.successRequestJsonCode(stSchSchoolList);
    }

    @ApiOperation(value = "（赋能用户浏览量增加）赋能用户浏览量增加-（*新增接口4.7）",notes = "（赋能用户浏览量增加）赋能用户浏览量增加-（*新增接口4.7）")
    @PostMapping("addView")
    public ResponseJsonCode addView(String schId){
        Integer addView = schoolService.addView(schId);
        return (addView == 1)?ResponseJsonCode.cusResponseJsonCode(200,"浏览量加一"):ResponseJsonCode.cusResponseJsonCode(500,"浏览量添加失败");
    }
}

