/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

/**
 * 验证提供器，Spring用它来生成验证管理器
 *
 * @author Diluka
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Spring的MD5计算和比对工具
     */
    @Autowired
    private Md5PasswordEncoder md5PasswordEncoder;

    @Transactional
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName(); //组合用户名
        //从数据库找到的用户
        UserDetails userDetails = null;
        if (username != null) {
            userDetails = userDetailsService.loadUserByUsername(username);
        }

        if (userDetails == null) {
            return null;//返回值如果为null，则会交给其他验证器处理
            //throw new UsernameNotFoundException("用户名/密码无效");
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("用户已被禁用");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("账号已过期");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("账号已被锁定");
        } else if (!userDetails.isCredentialsNonExpired()) {
            throw new LockedException("凭证已过期");
        }

        //数据库用户的密码
        String encPass = userDetails.getPassword();

        //与authentication里面的credentials相比较
        if (!md5PasswordEncoder.isPasswordValid(encPass, token.getCredentials().toString(), null)) {
            throw new BadCredentialsException("Invalid username/password");
        }

        //授权
        return new UsernamePasswordAuthenticationToken(userDetails, encPass, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.getName().equals(authentication.getName());
    }

}
