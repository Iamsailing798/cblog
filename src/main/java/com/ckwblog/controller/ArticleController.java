package com.ckwblog.controller;

import com.ckwblog.service.ArticleService;
import com.ckwblog.vo.Result;
import com.ckwblog.vo.params.ArticleParam;
import com.ckwblog.vo.params.PageParams;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//json数据交互
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams)
    {
        return articleService.listArticle(pageParams);
    }

    @PostMapping("hot")
    public Result hotArticles()
    {
        int limit=5;
        return articleService.hotArticle(limit);
    }

    @PostMapping("new")
    public Result newArticles()
    {
        int limit=5;
        return articleService.newArticles(limit);
    }

    @PostMapping("listArchives")
    public Result listArchives()
    {
        return articleService.listArchives();
    }

    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    //接口url：/articles/publish
    //
    //请求方式：POST
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){

        return articleService.publish(articleParam);
    }

    //编辑文章接口 对接在前端api文件夹
    @PostMapping("{id}")
    public Result articleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }
}
