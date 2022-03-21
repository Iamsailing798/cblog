package com.ckwblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ckwblog.dao.pojo.Category;
import com.ckwblog.service.CategoryService;
import com.ckwblog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // /categorys
    @GetMapping
    public Result categories(){
        return categoryService.findAll();
    }

    @GetMapping("detail")
    public Result categoriesDetail(){
        return categoryService.findAllDetail();
    }

    ///category/detail/{id}
    @GetMapping("detail/{id}")
    public Result categoryDetailById(@PathVariable("id") Long id){
        return categoryService.categoryDetailById(id);
    }

    /**
     * 后台显示分类
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("list")
    public Result typeList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {

        Page page = new Page(currentPage, pageSize);
        IPage pageData = categoryService.page(page, new QueryWrapper<>());
        return Result.success(pageData);
    }

    /**
     * 增加分类
     */
    @PostMapping("create")
    public Result createType(@Validated @RequestBody Category type){
        if(type==null){
            return Result.fail(10001,"不能为空");
        }
        else{
            categoryService.saveOrUpdate(type);
        }
        return Result.success(null);
    }

    /**
     * 修改分类
     */

    @PostMapping("update")
    public Result updateType(@Validated @RequestBody Category type){
        if(type==null){
            return Result.fail(10001,"不能为空");
        }
        else{
            categoryService.saveOrUpdate(type);
        }
        return Result.success(null);
    }


    /**
     * 删除分类
     */
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {

        if (categoryService.removeById(id)) {
            return Result.success(null);
        } else {
            return Result.fail(10001,"删除失败");
        }

    }
}
