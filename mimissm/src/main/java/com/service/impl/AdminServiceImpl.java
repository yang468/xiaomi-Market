package com.service.impl;/*
 *   2022/1/25
 */

import com.mapper.AdminMapper;
import com.pojo.Admin;
import com.pojo.AdminExample;
import com.service.AdminService;
import com.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    //在业务逻辑层中，一定要创建数据访问层对象mapper
    @Resource
    private AdminMapper adminMapper;
    @Override
    public Admin login(String name, String pwd) {
        Admin admin;
        //根据传入的用户名到数据库中查找响应的用户对象,条件查询需要AdminExample对象
        AdminExample example = new AdminExample();
        example.createCriteria().andANameEqualTo(name);
        List<Admin> list= adminMapper.selectByExample(example);
        if(list.size()>0){
            admin=list.get(0);
            //查询到用户之后要进行密码比对
            String miPwd = MD5Util.getMD5(pwd);
            if(miPwd.equals(admin.getaPass())){
                return admin;
            }
        }
        return null;
    }
}
