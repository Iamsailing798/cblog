package com.ckwblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ckwblog.dao.pojo.Category;
import com.ckwblog.dao.pojo.Tag;
import com.ckwblog.service.TagService;
import com.ckwblog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    public Result hot()
    {
        int limit=6;
        return tagService.hots(limit);
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }

    /**
     * 后台显示标签
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("list")
    public Result typeList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {

        Page page = new Page(currentPage, pageSize);
        IPage pageData = tagService.page(page, new QueryWrapper<>());
        return Result.success(pageData);
    }

    /**
     * 增加标签
     */
    @PostMapping("create")
    public Result createType(@Validated @RequestBody Tag tag){
        if(tag==null){
            return Result.fail(10001,"不能为空");
        }
        else{
            tagService.saveOrUpdate(tag);
        }
        return Result.success(null);
    }

    /**
     * 修改标签
     */

    @PostMapping("update")
    public Result updateType(@Validated @RequestBody Tag tag){
        if(tag==null){
            return Result.fail(10001,"不能为空");
        }
        else{
            tagService.saveOrUpdate(tag);
        }
        return Result.success(null);
    }


    /**
     * 删除标签
     */
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {

        if (tagService.removeById(id)) {
            return Result.success(null);
        } else {
            return Result.fail(10001,"删除失败");
        }

    }
}
