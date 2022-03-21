package com.ckwblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckwblog.dao.pojo.Tag;
import com.ckwblog.vo.Result;
import com.ckwblog.vo.TagVo;

import java.util.List;

public interface TagService extends IService<Tag> {

    List<TagVo> findTagsByArticleId(Long articleId);

    //最热标签 及数量
    Result hots(int limit);

    //查询所有的文章标签
    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
