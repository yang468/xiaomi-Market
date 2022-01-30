package com.controller;/*
 *   2022/1/25
 */

import com.pojo.Admin;
import com.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminAction {
    //切记，在界面层一定会有业务逻辑层的对象
    @Resource
    AdminService service;
    //实现登录判断，并进行跳转
    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin = service.login(name, pwd);
        if(admin!=null){
            //登录成功
            request.setAttribute("admin",admin);
            return "main";
        }else{
            //登录失败
            request.setAttribute("errmsg","用户名或密码不正确");
            return "login";
        }
    }
}
