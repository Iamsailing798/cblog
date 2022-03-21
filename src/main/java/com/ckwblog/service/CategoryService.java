package com.ckwblog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ckwblog.dao.pojo.Category;
import com.ckwblog.vo.CategoryVo;
import com.ckwblog.vo.Result;

public interface CategoryService extends IService<Category> {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);
}
