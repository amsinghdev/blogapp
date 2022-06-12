package com.amit.blogapp.utils;

import com.amit.blogapp.entity.Post;
import com.amit.blogapp.payload.PostDto;

public  class PostUtils {
    public static Post mapToEntity(PostDto postDto){
        System.out.println("postDto...."+postDto.toString());
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        System.out.println("post...."+post.toString());
        return post;
    }
    public static PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }
}
