package com.ckwblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckwblog.Utils.UserThreadLocal;
import com.ckwblog.dao.mapper.CommentMapper;
import com.ckwblog.dao.pojo.Comment;
import com.ckwblog.dao.pojo.SysUser;
import com.ckwblog.service.CommentsService;
import com.ckwblog.service.SysUserService;
import com.ckwblog.vo.CommentListVo;
import com.ckwblog.vo.CommentVo;
import com.ckwblog.vo.Result;
import com.ckwblog.vo.UserVo;
import com.ckwblog.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl extends ServiceImpl<CommentMapper,Comment> implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result CommentsByArticleId(Long id)
    {
        /**
         * 1. 根据文章id 查询 评论列表 从 comment 表中查询
         * 2. 根据作者的id 查询作者的信息
         * 3. 判断 如果 level = 1 要去查询它有没有子评论
         * 4. 如果有 根据评论id 进行查询 （parent_id）
         */
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        queryWrapper.orderByDesc(Comment::getCreateDate);
        List<Comment> commentList=commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList=copyList(commentList);
        return Result.success(commentVoList);
    }

    /**
     * 添加创造comment commentParam接受前端的参数
     * @param commentParam
     * @return
     */
    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);
    }

    /**
     * 后台评论管理 返回评论列表到前端
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Result commentList(Integer currentPage,Integer pageSize) {
        /*
         */
        Page page = new Page(currentPage, pageSize);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Comment::getArticleId,id);
//        queryWrapper.eq(Comment::getLevel,1);
        queryWrapper.orderByDesc(Comment::getCreateDate);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentListVo> commentListVoList = copyList_commentListVo(comments);
        return Result.success(commentListVoList);
    }

    /**
     * 文章评论 制作commentVo列表
     * @param commentList
     * @return
     */
    public List<CommentVo> copyList(List<Comment> commentList)
    {
        List<CommentVo> commentVoList=new ArrayList<>();
        for(Comment comment:commentList)
        {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    /**
     * 后台评论管理 制作commentListVo 列表
     * @param commentList
     * @return
     */
    public List<CommentListVo> copyList_commentListVo(List<Comment> commentList)
    {
        List<CommentListVo> commentVoList=new ArrayList<>();
        for(Comment comment:commentList)
        {
            commentVoList.add(copy_commentListVo(comment));
        }
        return commentVoList;
    }

    /**
     * 文章评论 数据库单个comment转为commentVo
     * @param comment
     * @return
     */
    public CommentVo copy(Comment comment)
    {
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        //作者信息
        Long authorId=comment.getAuthorId();
        commentVo.setAuthor(this.sysUserService.findUserVoById(authorId));
        //子评论
        Integer level=comment.getLevel();
        if(1==level)
        {
            Long id=comment.getId();
            List<CommentVo> commentVoList=findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //to user
        if(level>1)
        {
            Long toUid = comment.getToUid();
            UserVo toUserVo = this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
//            Long id=comment.getId();
//            UserVo toUser=new UserVo();
//            toUser=this.sysUserService.findUserVoById(id);
//            commentVo.setToUser(toUser);
        }
        return commentVo;
    }

    /**
     * 后台评论管理 数据库单个comment转为commentListVo
     * @param comment
     * @return
     */
    public CommentListVo copy_commentListVo(Comment comment)
    {
        CommentListVo commentListVo=new CommentListVo();
        BeanUtils.copyProperties(comment,commentListVo);
        commentListVo.setId(String.valueOf(comment.getId()));
        //作者信息
        Long authorId=comment.getAuthorId();
        //设置在后台管理中显示的昵称
        commentListVo.setNickname(this.sysUserService.findUserVoById(authorId).getNickname());


        //子评论
//        Integer level=comment.getLevel();
//        if(1==level)
//        {
//            Long id=comment.getId();
//            List<CommentVo> commentVoList=findCommentsByParentId(id);
//            commentListVo.setChildrens(commentVoList);
//        }
        //to user
//        if(level>1)
//        {
//            Long toUid = comment.getToUid();
//            UserVo toUserVo = this.sysUserService.findUserVoById(toUid);
//            commentListVo.setToUser(toUserVo);
//            Long id=comment.getId();
//            UserVo toUser=new UserVo();
//            toUser=this.sysUserService.findUserVoById(id);
//            commentVo.setToUser(toUser);
//        }
        return commentListVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        return copyList(commentMapper.selectList(queryWrapper));
    }

}
