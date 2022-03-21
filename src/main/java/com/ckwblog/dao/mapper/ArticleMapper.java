package com.ckwblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ckwblog.dao.dos.Archives;
import com.ckwblog.dao.pojo.Article;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;


public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();

    IPage<Article> listArticle(Page<Article> page,
                               Long categoryId,
                               Long tagId,
                               String year,
                               String month
                               );


}
