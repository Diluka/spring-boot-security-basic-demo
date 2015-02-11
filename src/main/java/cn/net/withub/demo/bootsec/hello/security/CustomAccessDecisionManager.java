/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.security;

import java.util.Collection;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 访问决策器
 *
 * @author Diluka
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {

    //访问决策方法，无异常则通过，否则抛出拒绝访问异常出现错误页面，或者权限不足异常重定向到登录页面
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            //没定义则无权限
            throw new AccessDeniedException("Access Dendied");
        }

        //所请求的资源拥有的权限(一个资源对多个权限)
        for (ConfigAttribute configAttribute : configAttributes) {
            //访问所请求资源所需要的权限
            String needPermission = configAttribute.getAttribute();

            System.out.println("needPermission is " + needPermission);

            //用户所拥有的权限authentication
            for (GrantedAuthority ga : authentication.getAuthorities()) {

                if (needPermission.equals(ga.getAuthority())) {
                    return;
                }

            }

        }

        //没有权限
        throw new AccessDeniedException("Access Dendied");
        //throw new InsufficientAuthenticationException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
