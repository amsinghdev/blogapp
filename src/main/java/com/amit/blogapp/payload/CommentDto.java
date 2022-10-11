package com.amit.blogapp.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private long id;

    @NotEmpty(message = "name should not be null or empty")
    private String name;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "please provide a valid email")
    private String email;

    @NotEmpty(message = "comment body should not be empty")
    @Size(min=10,message = "comment body should have minimum 10 characters")
    private String body;
}
