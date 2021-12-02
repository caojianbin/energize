package com.showtime.serviceenergize.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.showtime.common.model.ResultEnum;
import com.showtime.common.model.dto.ResponseJsonCode;
import com.showtime.serviceenergize.config.shiro.CustomJWTFilter;
import com.showtime.serviceenergize.entity.StEmpowermentUserTable;
import com.showtime.serviceenergize.entity.dto.EmpowermentUserRoleVo;
import com.showtime.serviceenergize.entity.vo.StPermissiondVo;
import com.showtime.serviceenergize.mapper.StEmpowermentUserTableMapper;
import com.showtime.serviceenergize.service.StEmpowermentUserTableService;
import com.showtime.serviceenergize.utils.JWTUtil;
import com.showtime.serviceenergize.utils.JedisUtil;
import com.showtime.serviceenergize.utils.UserConst;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 赋能管理后台用户表 服务实现类
 * </p>
 *
 * @author cjb
 * @since 2019-12-31
 */
@Service
public class StEmpowermentUserTableServiceImpl extends ServiceImpl<StEmpowermentUserTableMapper, StEmpowermentUserTable> implements StEmpowermentUserTableService {

    @Autowired
    private StEmpowermentUserTableMapper tableMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private CustomJWTFilter customJWTFilter;

    @Autowired
    private HttpServletResponse httpServletResponse;

    private final String USER_TYPE = "1";
    /**
     * 登录接口 实现层
     *
     * @param userPhone 账号
     * @param password  密码
     * @return
     */
    @Override
    public ResponseJsonCode login(String userPhone, String password) {
        //判断用户名和账号是否为空
        if (StringUtil.isEmpty(userPhone) || StringUtil.isEmpty(password)) {
            //为空返回错误提示,账号密码不能为空
            return ResponseJsonCode.creatResponseJsonCode(500, ResultEnum.USERNAME_NOT_EMPTY.getMsg());
        }
        StEmpowermentUserTable adminAccount = this.getOne(new QueryWrapper<StEmpowermentUserTable>().eq("user_phone", userPhone));
        if (adminAccount == null) {
            //为空返回错误提示,账号密码不存在
            return ResponseJsonCode.creatResponseJsonCode(500, ResultEnum.USER_UNEXIST.getMsg());
        } else {
            //不为空的时候，判断账号密码是否正确
            if (userPhone.equals(adminAccount.getUserPhone()) && password.equals(adminAccount.getUserPassWord())) {
                if (USER_TYPE.equals(adminAccount.getUserStatu())){
                    //账号密码都正确，登录成功
                    String token = customJWTFilter.loginCreateToken(userPhone , httpServletResponse);
                    return ResponseJsonCode.successRequestMsg(ResultEnum.LOGIN_SUCCESS.getMsg(), adminAccount);
                }else {
                    //账号被禁用或者假删除了
                    return ResponseJsonCode.successRequestMsg(ResultEnum.USER_DISABLED.getMsg(), adminAccount);
                }

            } else {
                //账号或者密码错误
                return ResponseJsonCode.creatResponseJsonCode(500, ResultEnum.LOGIN_FAIL.getMsg());
            }
        }
    }

    /**
     * 退出登录
     *
     * @return
     */

    @Override
    public void logOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String token = httpServletRequest.getHeader(UserConst.AUTHORIZATION);
        JedisUtil.delKey(UserConst.PREFIX_SHIRO_REFRESH_TOKEN + JWTUtil.getUsername(token));
    }

    /**
     * 查询用户角色信息
     *
     * @return code
     */
    @Override
    public Set<String> selectUserRoleByUserNames(String userName) {
        Set<String> roleCode = new HashSet<>();
        List<EmpowermentUserRoleVo> userRoleVos = tableMapper.selectUserRoleByUserNames(userName);
        if (userRoleVos != null){
            userRoleVos.forEach(roles->{
                roleCode.add(roles.getRoleCode());
            });
            return roleCode;
        }
        return null;
    }

    /**
     * 查询用户角色信息
     *
     * @return
     */
    @Override
    public EmpowermentUserRoleVo getUserRoleByUserNames(String userName) {
        return tableMapper.selectUserRoleByUserName(userName);
    }

    /**
     * 获取角色对应的权限
     *
     * @param roleId 角色id
     * @return
     */
    @Override
    public Set<String> selectPermissiondListByRole(Integer roleId) {
        Set<String> permissiondSet = new HashSet<>();
        List<StPermissiondVo> list = tableMapper.selectPermissiondListByRole(roleId);
        if (list != null){
            list.forEach(permissiondList ->{
                permissiondSet.add(permissiondList.getPermissionCode());
            });
            return permissiondSet;
        }
        return null;
    }
}
