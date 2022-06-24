package com.amit.blogapp.service;

import com.amit.blogapp.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(Long postId);
}
