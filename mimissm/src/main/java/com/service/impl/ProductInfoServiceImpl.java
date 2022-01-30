package com.service.impl;/*
 *   2022/1/25
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageInterceptor;
import com.mapper.ProductInfoMapper;
import com.pojo.ProductInfo;
import com.pojo.ProductInfoExample;
import com.pojo.vo.ProductInfoVo;
import com.service.ProductInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Resource
    ProductInfoMapper mapper;
    @Override
    public List<ProductInfo> getAll() {
        return mapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件使用PageHelper完成分页设置
        PageHelper.startPage(pageNum,pageSize);
        //进行pageInfo数据封装
        ProductInfoExample example = new ProductInfoExample();
        //设置排序，按主键降序，因为要找到新插入的数据

        example.setOrderByClause("p_id desc");
        //设置完排序后就可以取集合了
        List<ProductInfo> list = mapper.selectByExample(example);
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return mapper.insert(info);
    }

    @Override
    public ProductInfo getByID(int pid) {
        return mapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return mapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return mapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return mapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return mapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo,int pageSize) {
        //取出集合前设置PageHelper.startPage()属性
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = mapper.selectCondition(vo);
        return new PageInfo<>(list);
    }
}
