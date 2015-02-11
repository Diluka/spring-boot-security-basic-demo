/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.controller;

import cn.net.withub.demo.bootsec.hello.dao.UserinfoDAO;
import cn.net.withub.demo.bootsec.hello.entity.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Diluka
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserinfoDAO userinfoRepository;

    @Autowired
    private ApplicationContext ac;

    @RequestMapping("hello")
    public ModelAndView hello() {

        Iterable<Userinfo> users = userinfoRepository.findAll();
        return new ModelAndView("admin/hello", "users", users)
                .addObject("beans", ac.getBeanDefinitionNames())
                .addObject("courtCol", "法院名称")
                .addObject("usernameCol", "用户名")
                .addObject("pwdCol", "用户密码");
    }
}
