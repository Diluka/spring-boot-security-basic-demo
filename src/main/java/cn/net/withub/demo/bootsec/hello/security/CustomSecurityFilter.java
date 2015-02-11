/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;

/**
 * 验证拦截器
 *
 * @author Diluka
 */
public class CustomSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    @Override
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager); //To change body of generated methods, choose Tools | Templates.
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager newManager) {
        super.setAuthenticationManager(newManager); //To change body of generated methods, choose Tools | Templates.
    }

    @Autowired
    private SecurityMetadataSource securityMetadataSource;

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    @Override
    public void destroy() {
    }

    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        // object为FilterInvocation对象
        //1.获取请求资源的权限
        //执行Collection<ConfigAttribute> attributes = SecurityMetadataSource.getAttributes(object);
        //2.是否拥有权限
        //获取安全主体，可以强制转换为UserDetails的实例
        //1) UserDetails
        // Authentication authenticated = authenticateIfRequired();
        //this.accessDecisionManager.decide(authenticated, object, attributes);
        //用户拥有的权限
        //2) GrantedAuthority
        //Collection<GrantedAuthority> authenticated.getAuthorities()
        System.out.println("用户发送请求！ ");
        InterceptorStatusToken token = null;

        token = super.beforeInvocation(fi);

        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

}
