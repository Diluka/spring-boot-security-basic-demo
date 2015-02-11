/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.security;

import cn.net.withub.demo.bootsec.hello.dao.ResourceDAO;
import cn.net.withub.demo.bootsec.hello.entity.Resource;
import cn.net.withub.demo.bootsec.hello.entity.Roleinfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.Transactional;

/**
 * 资源权限元数据
 *
 * @author Diluka
 */
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ResourceDAO resourceRepository;

    private Map<String, Collection<ConfigAttribute>> resourceMap;

    /**
     * 数据库信息是否改变
     */
    public static boolean databaseChanged = true;

    @Transactional
    private void loadResourceMatchAuthority() {

        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

        for (Resource res : resourceRepository.findAll()) {
            Collection<ConfigAttribute> list;
            if (resourceMap.containsKey(res.getUrl())) {
                list = resourceMap.get(res.getUrl());
            } else {
                list = new ArrayList<ConfigAttribute>();
                resourceMap.put(res.getUrl(), list);
            }
            for (Roleinfo role : res.getRoleinfoList()) {
                list.add(new SecurityConfig(role.getAuthority()));
            }
        }

        databaseChanged = false;

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;

        HttpServletRequest request = fi.getRequest();

        System.out.println("requestUrl is " + fi.getRequestUrl());

        if (resourceMap == null || databaseChanged) {
            loadResourceMatchAuthority();
        }

        Collection<ConfigAttribute> attrs = new ArrayList<ConfigAttribute>();
        for (String urlPattern : resourceMap.keySet()) {
            //路径匹配器
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(urlPattern);
            if (matcher.matches(request)) {
                System.out.println("matched resource url patterns: " + urlPattern);
                attrs.addAll(resourceMap.get(urlPattern));
            }
        }

        return attrs;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
