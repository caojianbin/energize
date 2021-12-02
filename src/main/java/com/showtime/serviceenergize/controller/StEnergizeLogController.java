package com.showtime.serviceenergize.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.annotation.SystemControllerLog;
import com.showtime.serviceenergize.service.StEnergizeLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 赋能操作日志 前端控制器
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/stEnergizeLog")
@Api(tags = "赋能操作日志管理")
public class StEnergizeLogController {

    /**
     * 赋能操作日志 服务类
     */
    @Autowired
    private StEnergizeLogService energizeLogService;

    /**
     * 查询日志列表
     *
     * @param userName 用户名
     * @param roleType 角色类型
     * @param page     分页信息
     * @return list 结果集
     */
    @GetMapping("getEnergizeList.do")
    @ApiOperation(value = "查询日志列表",notes = "查询日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名",name = "userName",required = false,paramType = "qurey",dataType = "String"),
            @ApiImplicitParam(value = "角色类型",name = "roleType",required = false,paramType = "qurey",dataType = "Integer")
    })
    public ResponseJsonCode getEnergizeList(Page page, String userName, Integer roleType) {
        return ResponseJsonCode.successRequestJsonCode(energizeLogService.getEnergizeList(page,userName,roleType));
    }
}

