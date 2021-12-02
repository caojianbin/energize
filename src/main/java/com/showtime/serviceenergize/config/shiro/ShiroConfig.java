package com.showtime.serviceenergize.config.shiro;

import com.showtime.serviceenergize.filter.JWTFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName : ShiroConfig
 * @Description :Shiro配置类
 * @Author : jlp
 * @Date: 2019-12-11 19:24
 */
@Configuration
@AutoConfigureAfter(ShiroLifecycleBeanPostProcessorConfig.class)
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean factory(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        //设置我们自定义的JWT过滤器
        filterMap.put("jwt", new JWTFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        // 设置无权限时跳转的 url;
        factoryBean.setUnauthorizedUrl("/unauthorized/无权限");
        Map<String, String> filterRuleMap = new LinkedHashMap<>();

        //放行Swagger2页面
        filterRuleMap.put("/*.html","anon");
        filterRuleMap.put("/**/*.api","anon");
        filterRuleMap.put("/doc.html","anon");
        filterRuleMap.put("/doc/**","anon");
        filterRuleMap.put("/swagger-ui.html","anon");
        filterRuleMap.put("/swagger/**","anon");
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/swagger-resources/**","anon");
        filterRuleMap.put("/v2/**","anon");


        //放行登录接口
        filterRuleMap.put("/st/bg/login.do", "anon");
        //放行调用远程方法
        filterRuleMap.put("/ali/pay/**","anon");
        filterRuleMap.put("/fn/school/FeignClient/**","anon");
        filterRuleMap.put("/fn/user/FeignClient/**","anon");
        filterRuleMap.put("/st/school/web/merge/**","anon");
        filterRuleMap.put("/st/stSchSchool/**","anon");
        filterRuleMap.put("/energize/wx/pay/**","anon");

        // 访问 /unauthorized/** 不通过JWTFilter
        filterRuleMap.put("/unauthorized/**", "anon");

        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");
        //       filterRuleMap.put("/**", "anon");

        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    /**
     *@description 将自定义Realm注入安全管理器
     *@Param 自定义Realm
     *@return
     *@Author jlp
     *@Date 2019/12/12 10:10
     *@throws
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义 realm.
        securityManager.setRealm(customRealm());

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     *@description 开启Shiro的注解(如 @ RequiresRoles, @ RequiresPermissions), 需借助SpringAOP扫描使用Shiro注解的类, 并在必要时进行安全逻辑验证
     *      *   配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *@Param
     *@return
     *@Author jlp
     *@Date 2019/12/12 10:23
     *@throws
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     *@description 开启aop注解支持（通知器）
     *@Param securityManager 安全管理器
     *@return 
     *@Author jlp
     *@Date 2019/12/12 10:23
     *@throws 
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
