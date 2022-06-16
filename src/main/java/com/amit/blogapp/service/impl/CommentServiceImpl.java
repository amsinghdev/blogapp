package com.amit.blogapp.service.impl;

import com.amit.blogapp.entity.Comment;
import com.amit.blogapp.entity.Post;
import com.amit.blogapp.exception.ResourceNotFoundException;
import com.amit.blogapp.payload.CommentDto;
import com.amit.blogapp.repository.CommentRepository;
import com.amit.blogapp.repository.PostRepository;
import com.amit.blogapp.service.CommentService;
import com.amit.blogapp.utils.CommentUtil;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        //get the post from the db
        Post post  = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment = CommentUtil.mapToEntity(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return CommentUtil.mapToDto(newComment);
    }
}
