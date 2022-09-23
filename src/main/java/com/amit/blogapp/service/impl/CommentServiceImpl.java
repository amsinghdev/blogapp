package com.amit.blogapp.service.impl;

import com.amit.blogapp.entity.Comment;
import com.amit.blogapp.entity.Post;
import com.amit.blogapp.exception.BlogApiException;
import com.amit.blogapp.exception.ResourceNotFoundException;
import com.amit.blogapp.payload.CommentDto;
import com.amit.blogapp.repository.CommentRepository;
import com.amit.blogapp.repository.PostRepository;
import com.amit.blogapp.service.CommentService;
import com.amit.blogapp.utils.CommentUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment->CommentUtil.mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Comment comment = getValidComment(postId,commentId);
        return CommentUtil.mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        Comment comment = getValidComment(postId,commentId);
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment updateComment = commentRepository.save(comment);
        return CommentUtil.mapToDto(updateComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = getValidComment(postId,commentId);
        commentRepository.delete(comment);
    }

    private Comment getValidComment(Long postId,Long commentId){
        //get the post from db
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));

        //get the comment from the db
        Comment comment  = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment id does not belongs to post");
        }
        return comment;
    }
}
