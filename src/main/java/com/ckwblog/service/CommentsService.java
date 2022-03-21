package com.ckwblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckwblog.dao.pojo.Comment;
import com.ckwblog.vo.Result;
import com.ckwblog.vo.params.CommentParam;


public interface CommentsService extends IService<Comment> {

    /**
     * 根据文章id查询评论列表
     * @param id
     * @return
     */
    Result CommentsByArticleId(Long id);

    /**
     * 添加评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);

    Result commentList(Integer currentPage, Integer pageSize);
}
