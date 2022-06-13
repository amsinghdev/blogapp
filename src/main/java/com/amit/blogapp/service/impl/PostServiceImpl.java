package com.amit.blogapp.service.impl;

import com.amit.blogapp.entity.Post;
import com.amit.blogapp.exception.ResourceNotFoundException;
import com.amit.blogapp.payload.PostDto;
import com.amit.blogapp.payload.PostResponse;
import com.amit.blogapp.repository.PostRepository;
import com.amit.blogapp.service.PostService;
import com.amit.blogapp.utils.PostUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PostResponse getAllPost(int pageNo,int pageSize) {
        //create a pageable instance
        Pageable pageable  = PageRequest.of(pageNo,pageSize);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        List<PostDto>contnet =  postList.stream().map(post -> PostUtils.mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(contnet);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
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
