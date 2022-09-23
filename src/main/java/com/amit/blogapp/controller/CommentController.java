package com.amit.blogapp.controller;

import com.amit.blogapp.payload.CommentDto;
import com.amit.blogapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value ="postId" ) Long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentByPostId(@PathVariable(value="postId") Long postId,
                                                         @PathVariable(value="commentId") Long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId),HttpStatus.OK);
    }

    @PutMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "commentId") Long commentId,
                                                    @RequestBody CommentDto comment){
        return new ResponseEntity<>(commentService.updateComment(postId,commentId,comment),HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "commentId") Long commentId){
        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }
}
