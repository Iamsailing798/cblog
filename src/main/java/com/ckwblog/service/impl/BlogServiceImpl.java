package com.ckwblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckwblog.dao.mapper.BlogMapper;
import com.ckwblog.dao.pojo.Article;
import com.ckwblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author fanfanli
 * @date 2021-04-08
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Article> implements BlogService {
    @Autowired
    BlogMapper blogMapper;

//    /**
//     * 通过分类名查找属于该分类的博客list
//     *
//     * @param categoryName 分类名
//     * @return 博客list
//     */
//    @Override
//    public List<BlogInfo> getBlogInfoListByCategoryName(String categoryName) {
//        List<BlogInfo> blogInfos = blogMapper.getBlogByTypeName(categoryName);
//        return blogInfos;
//    }

}
