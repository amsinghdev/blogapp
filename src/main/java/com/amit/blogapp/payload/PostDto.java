package com.amit.blogapp.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {
    private long id;
    //title should not be null or empty
    //title should have at lest 2 characters
    @NotEmpty
    @Size(min=2,message = " post title should have at lest 2 characters")
    private String title;

    //description should not be null or empty
    //description should have at lest 10 characters
    @NotEmpty
    @Size(min = 10,message = "post description should have at lest 10 characters")
    private String description;

    //content should not be null or empty
    @NotEmpty(message = "content should not be null or empty")
    private String content;
    private Set<CommentDto> comments;
}
