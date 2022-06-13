package com.amit.blogapp.service;

import com.amit.blogapp.payload.PostDto;
import com.amit.blogapp.payload.PostResponse;

import java.util.List;

public interface PostService {
     PostDto createPost(PostDto postDto);
     PostResponse getAllPost(int pageNo, int pageSize,String sortBy,String sortDir);
     PostDto getPostById(long id);
     PostDto updatePost(PostDto postDto,long id);
     void deletePostById(long id);
}
