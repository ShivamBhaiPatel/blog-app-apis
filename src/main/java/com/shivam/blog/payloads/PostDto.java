package com.shivam.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.shivam.blog.entities.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private int postId;
	private String postTitle;
	private String content;
	private String imageName;
	private Date addedDate;
	private CategoryDto category;
	private UserDto user;
	private Set<Comment> comment=new HashSet<>();
}
