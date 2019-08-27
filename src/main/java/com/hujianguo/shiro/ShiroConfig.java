/**
 * @Project Name:shiro-first
 * @File Name:ShiroConfig.java
 * @Package Name:com.hujianguo.shiro.config
 * @Date: 2019-08-20 10:59:09
 * @author: 胡建国
 * Copyright (c) 2019, hujianguo@lidoo.com All Rights Reserved.
 */
package com.hujianguo.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author 胡建国
 * @Date: 2019-08-20 10:59:09
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Configuration
public class ShiroConfig {

    // Subject 主体 (把操作交给SecurityManager处理)
    // SecurityManager 安全管理器 (关联Realm)
    // Realm shiro连接数据的桥梁

    // 创建ShiroFilterBean
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {

        System.out.println("###########################ShiroConfiguration.shiroFilter()##########################################");

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilter.setSecurityManager(securityManager);
        /**
         * 常用过滤器
         * anon  无需验证
         * authc 必须认证才可以访问
         * user 如果使用remeberMe可以直接访问
         * perms 该资源必须得到资源权限才可以访问
         * role 该资源必须得到角色权限才可以访问
         */
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setUnauthorizedUrl("/403");
        shiroFilter.setSuccessUrl("/usersPage");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/druid/*", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/font-awesome/**", "anon");


        filterChainDefinitionMap.put("/**", "anon");

        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilter;
    }

    // 创建DefaultWebSecurityManager
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("realm") Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);

        return securityManager;
    }

    // 创建Realm
    @Bean("realm")
    public Realm realm(CredentialsMatcher credentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }


    /**
     * *
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * *
     * 配置以下两个bean
     * DefaultAdvisorAutoProxyCreator(可选)和
     * AuthorizationAttributeSourceAdvisor即可实现此功能
     * * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
