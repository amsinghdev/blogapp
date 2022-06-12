package com.amit.blogapp.service.impl;

import com.amit.blogapp.entity.Post;
import com.amit.blogapp.exception.ResourceNotFoundException;
import com.amit.blogapp.payload.PostDto;
import com.amit.blogapp.repository.PostRepository;
import com.amit.blogapp.service.PostService;
import com.amit.blogapp.utils.PostUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = postRepository.save(PostUtils.mapToEntity(postDto));
        System.out.println("post..."+post.toString());
        return PostUtils.mapToDto(post);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> PostUtils.mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post =  postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        return PostUtils.mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post = postRepository.save(post);
        return PostUtils.mapToDto(post);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        postRepository.delete(post);
    }
}
