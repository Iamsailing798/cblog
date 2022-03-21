package com.ckwblog.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentListVo {
    private String id;

    private String nickname;

    private String content;
    

//    private List<CommentVo> childrens;

    private String createDate;

//    private Integer level;

//    private UserVo toUser;
}
