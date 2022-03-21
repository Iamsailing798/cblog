package com.ckwblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ckwblog.dao.pojo.Comment;
import com.ckwblog.service.CommentsService;
import com.ckwblog.service.SysUserService;
import com.ckwblog.vo.Result;
import com.ckwblog.vo.UserVo;
import com.ckwblog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationSupport;
import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    private CommentsService commentService;

    @Autowired
    private SysUserService sysUserService;

    ///comments/article/{id}

    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id)
    {
        return commentService.CommentsByArticleId(id);
    }

    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentService.comment(commentParam);
    }

    /**
     * 获取评论列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/commentList")
    public Result getCommentListByPage(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10") Integer pageSize ) {
        Page page = new Page(currentPage, pageSize);
        IPage pageData = commentService.page(page, new QueryWrapper<Comment>().orderByDesc("create_date"));

//        List<Comment> CommentList=pageData.getRecords();
//
//        for (Comment comment:CommentList) {
//            UserVo userVo =sysUserService.findUserVoById(comment.getArticleId());
//            System.out.println(userVo.getNickname());
//        }


        return Result.success(commentService.commentList(currentPage,pageSize));

    }


    /**
     * 分页查询某个博客下的根评论
     */

    @GetMapping("/comment/detail")
    public Result getCommentListByPageId(@RequestParam(defaultValue = "1") Long blogId, @RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10") Integer pageSize ) {

        Page page = new Page(currentPage, pageSize);
        IPage pageData = commentService.page(page, new QueryWrapper<Comment>().eq("article_id",blogId).orderByDesc("create_date"));

        //Assert.notNull(blog, "该博客已删除！");
        return Result.success(pageData);
    }

    /**
     * 获取某个博客下的所有评论
     */
//    @GetMapping("/comment/{blogId}")
//    public Result getCommentByBlogId(@PathVariable(name = "blogId") Long blogId) {
//
//        //实体模型集合对象转换为VO对象集合
//        List<PageComment> pageComments = commentService.getPageCommentListByDesc(blogId, (long) -1);
//
//        for (PageComment pageComment : pageComments) {
//
//            List<PageComment> reply = commentService.getPageCommentList(blogId, pageComment.getId());
//            pageComment.setReplyComments(reply);
//        }
//        //Assert.notNull(blog, "该博客已删除！");
//        return Result.success(pageComments);
//
//    }



    /**
     * 修改评论
     */

    @RequestMapping("update")
    public Result updateById(@Validated @RequestBody Comment comment){
        if(comment==null){
            return Result.fail(10001,"不能为空");
        }
//        Friend temp = new Friend();
//        BeanUtil.copyProperties(friend, temp);
        commentService.saveOrUpdate(comment);
        return Result.success(null);

    }

    /**
     * 删除评论
     */

    @RequestMapping("delete/{id}")
    public Result delete(@PathVariable(name = "id")String id){

        if (commentService.removeById(id)) {
            return Result.success(null);
        } else {
            return Result.fail(10001,"删除失败");
        }

    }
}
