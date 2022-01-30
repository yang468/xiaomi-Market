package com.controller;/*
 *   2022/1/25
 */

import com.github.pagehelper.PageInfo;
import com.pojo.ProductInfo;
import com.pojo.vo.ProductInfoVo;
import com.service.ProductInfoService;
import com.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    //每页显示的记录数
    public static final int PAGE_SIZE = 5;
    String saveFileName="";
    @Resource
    ProductInfoService service;
    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = service.getAll();
        request.setAttribute("list",list);
        return "product";
    }
    //默认显示第一页的五条数据
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info=null;
        Object vo = request.getSession().getAttribute("prodVo");
        if(vo!=null){
            info = service.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }
        else info = service.splitPage(1,PAGE_SIZE);
        request.setAttribute("info",info);
        return "product";
    }
    //ajax分页的翻页处理
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session){
        //取得当前page数据
        PageInfo info = service.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",info);
    }
    //异步ajax的上传
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        //提取文件名UUID+后缀，生成文件名
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中图片存储的路径
        String path = request.getServletContext().getRealPath("/image_big");
        //转存
        try {
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回客户端json对象，封装图片的路径，为了在页面实现回显
        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);
        return object.toString();
    }
    //商品增加
    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        //info对象有表单提交的五个数据，异步ajax的图片，图片名称和上架时间
        int num=0;
        try {
            num = service.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","增加成功");
        }
        else {
            request.setAttribute("msg","增加失败");
        }
        saveFileName="";
        //跳到分页显示action上
        return "forward:/prod/split.action";
    }
    //根据主键查找
    @RequestMapping("/one")
    public String one(int pid,ProductInfoVo vo, Model model,HttpSession session){
        ProductInfo info = service.getByID(pid);
        model.addAttribute("prod",info);
        session.setAttribute("prodVo",vo);
        return "update";
    }
    //完成更新
    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        if(!saveFileName.equals("")){
            info.setpImage(saveFileName);
        }
        int num=-1;
        try {
            num= service.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","更新成功");
        }
        else {
            request.setAttribute("msg","更新失败");
        }
        saveFileName="";
        return "forward:/prod/split.action";
    }
    //完成删除
    @RequestMapping("/delete")
    public String delete(int pid,HttpServletRequest request){
        int num=-1;
        try {
            num=service.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","删除成功");
        }else{
            request.setAttribute("msg","删除失败");
        }
        return "forward:/prod/split.action";
    }
    //完成批量删除商品
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request){
        //将上传的字符串截开，形成id的字符数组
        String[] ps = pids.split(",");
        int num=-1;
        try {
            num= service.deleteBatch(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","批量删除成功");
        }else{
            request.setAttribute("msg","批量删除失败");
        }
        return "forward:/prod/split.action";
    }
    //多条件查询实现
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list = service.selectCondition(vo);
        session.setAttribute("list",list);
    }
}
