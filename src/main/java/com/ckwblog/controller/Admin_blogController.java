package com.ckwblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ckwblog.dao.pojo.Article;
import com.ckwblog.service.BlogService;
import com.ckwblog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Admin_blogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogList")
    public Result blogList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {

        Page page = new Page(currentPage, pageSize);
        IPage pageData = blogService.page(page);

        return Result.success(pageData);
    }

    @GetMapping("/blog/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {
        if (blogService.removeById(id)) {
            return Result.success(null);
        } else {
            return Result.fail(10001,"删除失败");
        }

    }
}
