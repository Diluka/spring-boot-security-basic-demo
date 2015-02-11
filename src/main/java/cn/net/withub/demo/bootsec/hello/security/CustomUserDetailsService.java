/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.security;

import cn.net.withub.demo.bootsec.hello.common.Constant;
import cn.net.withub.demo.bootsec.hello.dao.UserinfoDAO;
import cn.net.withub.demo.bootsec.hello.entity.Userinfo;
import cn.net.withub.demo.bootsec.hello.entity.UserinfoPK;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务
 *
 * @author Diluka
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserinfoDAO userinfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //username为组合的联合主键：<法院编号>#<用户名>，例如：1#test
        if (!username.contains(Constant.COURT_NO_USERNAME_SEPARATOR)) {
            //不支持的格式，交给其他数据源处理（如内置的内存数据）
            return null;
        }

        String c = username.substring(0, username.indexOf(Constant.COURT_NO_USERNAME_SEPARATOR));
        String u = username.substring(username.indexOf(Constant.COURT_NO_USERNAME_SEPARATOR) + 1);

        if (Strings.isNullOrEmpty(c) || Strings.isNullOrEmpty(u)) {
            //格式不支持
            return null;
            //throw new UsernameNotFoundException("c: " + c + " u: " + u);
        }

        UserinfoPK upk;

        try {
            upk = new UserinfoPK(Integer.parseInt(c), u);
        } catch (Exception e) {
            e.printStackTrace();
            //数据格式不支持
            return null;
        }

        Userinfo user = userinfoRepository.findOne(upk);

        if (user == null) {
            throw new UsernameNotFoundException("c: " + c + " u: " + u);
        }

        return user;

    }

}
