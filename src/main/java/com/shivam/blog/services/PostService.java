package com.shivam.blog.services;

import com.shivam.blog.payloads.PostDto;
import com.shivam.blog.payloads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto);
	PostDto updatePost(PostDto postDto);
	void deletePost(Integer postId);
	
	// pagination
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	PostDto getPostById(Integer postId);
	
	//search post
	//pagination
	PostResponse searchPostByTitle(String title, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	PostResponse getPostByCategoryId(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	PostResponse getPostByUserId(Integer userId , Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	


}
