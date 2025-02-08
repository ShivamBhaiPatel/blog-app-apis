package com.shivam.blog.services;

import com.shivam.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Integer postId);
	CommentDto updateComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);

}
