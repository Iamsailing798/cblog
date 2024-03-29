package com.ckwblog.vo;

import com.ckwblog.dao.pojo.Comment;
import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
    private String id;

    private UserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}
