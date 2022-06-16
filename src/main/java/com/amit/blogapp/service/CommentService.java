package com.amit.blogapp.service;

import com.amit.blogapp.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);
}
