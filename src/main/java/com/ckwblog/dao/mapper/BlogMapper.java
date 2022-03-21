package com.ckwblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ckwblog.dao.pojo.Article;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fanfanli
 * @date 2021-04-08
 */
@Mapper
@Repository
public interface BlogMapper extends BaseMapper<Article> {
//    /**
//     * 根据分类查询博客
//     */
//    List<BlogInfo> getBlogByTypeName(String typeName);

}
