package com.service;/*
 *   2022/1/25
 */

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageInterceptor;
import com.pojo.ProductInfo;
import com.pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {
    //显示全部商品（不分页）
    List<ProductInfo> getAll();
    //分页功能的开发，需要传入当前页和页大小
    PageInfo splitPage(int pageNum, int pageSize);
    //增加商品
    int save(ProductInfo info);
    //按主键id查询商品
    ProductInfo getByID(int pid);
    //更新商品
    int update(ProductInfo info);
    //单个商品删除
    int delete(int pid);
    //批量删除商品
    int deleteBatch(String[] ids);
    //多条件商品查询
    List<ProductInfo> selectCondition(ProductInfoVo vo);
    //多条件查询分页
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo,int pageSize);
}
