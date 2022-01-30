package com.service;/*
 *   2022/1/25
 */

import com.pojo.Admin;

public interface AdminService {
    //完成登录判断
    Admin login(String name, String pwd);
}
