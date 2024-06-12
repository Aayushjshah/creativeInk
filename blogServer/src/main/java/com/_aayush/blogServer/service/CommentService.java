package com._aayush.blogServer.service;

import com._aayush.blogServer.entity.Comment;

import java.util.List;

public interface CommentService {
    public Comment createComment(Long postId , String postedBy, String content);
    public List<Comment> getCommentsByPostId(long postId);
}
