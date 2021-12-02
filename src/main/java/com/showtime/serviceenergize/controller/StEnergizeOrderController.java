package com.showtime.serviceenergize.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.annotation.SystemControllerLog;
import com.showtime.serviceenergize.service.StEnergizeOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 书唐赋能订单表 前端控制器
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/stEnergizeOrder")
@Api(tags = "书唐赋能订单管理")
public class StEnergizeOrderController {

    /**
     * 书唐赋能订单表 服务类
     */
    @Autowired
    private StEnergizeOrderService orderService;

    /**
     * 统计金额
     *
     * @return
     */
    @GetMapping("countEnergizeMoney.do")
    @ApiOperation(value = "统计金额",notes = "统计金额,作者：卑微的小曹")
    public ResponseJsonCode countEnergizeMoney() {
        return ResponseJsonCode.successRequestJsonCode(orderService.countEnergizeMoney());
    }

    /**
     * 根据条件查询订单数据
     *
     * @param ctName    商户名称
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param page      分页信息
     * @return List结果集
     */
    @SystemControllerLog(description = "查询订单数据")
    @GetMapping("slelcOrdertListByCondition.do")
    @ApiOperation(value = "查询订单数据",notes = "查询订单数据,作者：卑微的小曹")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "ctName",name = "商户名称",required = false,paramType = "qurey"),
            @ApiImplicitParam(value = "startTime",name = "开始时间",required = false,paramType = "qurey"),
            @ApiImplicitParam(value = "endTime",name = "结束时间",required = false,paramType = "qurey")
    })
    public ResponseJsonCode slelcOrdertListByCondition(Page page, String ctName, String startTime, String endTime){
        return ResponseJsonCode.successRequestJsonCode(orderService.slelcOrdertListByCondition(page,ctName,startTime,endTime));
    }
}

