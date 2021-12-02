package com.showtime.serviceenergize.controller;

import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.annotation.SystemControllerLog;
import com.showtime.serviceenergize.service.StEmpowermentUserTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;

/**
 * <p>
 * 书唐赋能登录接口
 * </p>
 *
 * @author cjb
 * @since 2019-11-28
 */
@RestController
@Slf4j
@RequestMapping("st/bg")
@Api(tags = "赋能登录接口")
public class StEnergizeLoginController {
    /**
     * 赋能管理员账号表 服务类
     */
    @Autowired
    private StEmpowermentUserTableService adminAccountService;

    @SystemControllerLog(description = "用户登录")
    @PostMapping("login.do")
    @ApiOperation(value = "书唐赋能登录接口",notes = "书唐赋能登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",value = "账号",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value = "密码",paramType = "query",required = true,dataType = "String")})
    public ResponseJsonCode login(String account, String password,ServletResponse response){
        log.info("用户的用户名为:{},用户的密码为:{}",account,password);
        //解密
//        account = AESUtil.getPassword(account);
//        password = AESUtil.getPassword(password);
        ResponseJsonCode login = adminAccountService.login(account, password );
        return login;
    }

    @SystemControllerLog(description = "退出登录")
    @PostMapping("logOut.do")
    @ApiOperation(value = "退出登录",notes = "退出登录")
    public ResponseJsonCode logOut(){
        adminAccountService.logOut();
        return ResponseJsonCode.successRequestJsonCode(1);
    }
}
