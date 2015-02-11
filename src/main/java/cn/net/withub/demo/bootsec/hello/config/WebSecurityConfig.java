/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.config;

import cn.net.withub.demo.bootsec.hello.security.CustomAccessDecisionManager;
import cn.net.withub.demo.bootsec.hello.security.CustomAuthenticationProvider;
import cn.net.withub.demo.bootsec.hello.security.CustomFilterInvocationSecurityMetadataSource;
import cn.net.withub.demo.bootsec.hello.security.CustomSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * Web安全配置
 *
 * @author Diluka
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //将管理器暴露为Bean
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 自定义授权提供器
     *
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        AuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
        return authenticationProvider;
    }

    /**
     * 简单的权限设置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN") //“/admin/”开头的URL必须要是管理员用户，譬如”admin”用户
                .anyRequest().authenticated() //所有其他的URL都需要用户进行验证
                .and().formLogin() //使用Java配置默认值设置了基于表单的验证。使用POST提交到”/login”时，需要用”username”和”password”进行验证。
                .loginPage("/login").permitAll() //注明了登陆页面，意味着用GET访问”/login”时，显示登陆页面
                .and().logout().permitAll() //任何人(包括没有经过验证的)都可以访问”/login”和”/login?error”。permitAll()是指用户可以访问formLogin()相关的任何URL。
                .and().exceptionHandling().accessDeniedPage("/error.html");
    }

    /**
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //配置过滤
        web
                .ignoring()
                .antMatchers("/resources/**", "/js/**", "/css/**", "/image/**");
    }

    /**
     * 添加内置用户
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //”roles()”方法会自动添加”ROLE_“
        //内置验证器
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN", "A");//实际数据为：ROLE_ADMIN, ROLE_A

        //自定义验证器
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public Md5PasswordEncoder md5PasswordEncoder() {
        return new Md5PasswordEncoder();
    }

    @Bean
    public CustomSecurityFilter securityFilter() {
        CustomSecurityFilter securityFilter = new CustomSecurityFilter();
        return securityFilter;
    }

    @Bean
    public CustomFilterInvocationSecurityMetadataSource securityMetadataSource() {
        return new CustomFilterInvocationSecurityMetadataSource();
    }

    @Autowired
    @Bean
    public CustomAccessDecisionManager accessDecisionManager() {
        return new CustomAccessDecisionManager();
    }
//    @Bean
//    public AuthenticationEntryPoint ajaxAuthenticationEntryPoint() {
//        return new AuthenticationEntryPoint() {
//            @Override
//            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            }
//        };
//    }
//
//    @Bean
//    public RequestMatcher ajaxRequestMatcher() {
//        return new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest");
//    }

}
