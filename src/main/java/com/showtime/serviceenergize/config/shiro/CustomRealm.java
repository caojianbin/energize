package com.showtime.serviceenergize.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.showtime.serviceenergize.entity.StEmpowermentUserTable;
import com.showtime.serviceenergize.service.StEmpowermentUserTableService;
import com.showtime.serviceenergize.utils.JWTUtil;
import com.showtime.serviceenergize.utils.UserConst;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName : CustomRealm
 * @Description :自定义Realm
 * @Author : jlp
 * @Date: 2019-12-11 19:23
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private StEmpowermentUserTableService userTableService;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     *@description  用户身份认证
     *@Author jlp
     *@Date 2019/12/11 16:37
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得userPhone，用于和数据库进行对比
        String userPhone = JWTUtil.getUsername(token);
        try {
            if(userPhone == null){
                throw new AuthenticationException("Token无效！");
            }
            JWTUtil.verify(token, userPhone);

            StEmpowermentUserTable user = userTableService.getOne(new QueryWrapper<StEmpowermentUserTable>().eq(UserConst.USER_PHONE , userPhone));
            if (user == null || user.getUserPassWord() == null) {
                throw new AuthenticationException("该用户不存在！");
            }
            if (Integer.valueOf(user.getUserStatu()) == 2) {
                throw new AuthenticationException("该用户已被封号！");
            }
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }

    /**
     *@description  检测用户权限（checkRole,checkPermission等）
     *@Author jlp
     *@Date 2019/12/11 16:37
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userPhone = JWTUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
//        List<Role> roleSet = userService.findRoleListByUsername(userPhone);
//        Set<String> roleNames = userService.getRoleSetByRoleList(roleSet);
//        每个角色拥有默认的权限
//        Set<String> permissionNames = userService.getPermissionSetByRoleList(roleSet);
        //设置该用户拥有的角色和权限
//        info.setRoles(roleNames);
//        info.setStringPermissions(permissionNames);
        return info;
    }
}
