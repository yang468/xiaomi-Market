package com.service.impl;/*
 *   2022/1/25
 */

import com.mapper.ProductTypeMapper;
import com.pojo.ProductType;
import com.pojo.ProductTypeExample;
import com.service.ProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Resource
    ProductTypeMapper mapper;
    @Override
    public List<ProductType> getAll() {
        return mapper.selectByExample(new ProductTypeExample());
    }
}
