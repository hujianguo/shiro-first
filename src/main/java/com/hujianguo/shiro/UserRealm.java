/**
 * @Project Name:shiro-first
 * @File Name:UserRealm.java
 * @Package Name:com.hujianguo.shiro
 * @Date: 2019-08-21 17:19:59
 * @author: 胡建国
 * Copyright (c) 2019, hujianguo@lidoo.com All Rights Reserved.
 */
package com.hujianguo.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author 胡建国
 * @Date: 2019-08-21 17:19:59
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class UserRealm extends AuthorizingRealm {


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


        return null;
    }
}
