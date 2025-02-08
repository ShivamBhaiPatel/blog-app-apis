package com.shivam.blog.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shivam.blog.entities.Comment;
import com.shivam.blog.entities.Post;
import com.shivam.blog.exceptions.ResourceNotFoundException;
import com.shivam.blog.payloads.CommentDto;
import com.shivam.blog.repository.CommentRepo;
import com.shivam.blog.repository.PostRepo;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedPost=this.commentRepo.save(comment);
		return this.modelMapper.map(savedPost, CommentDto.class);
		
//		Set<Comment> comments=post.getComments();
//		comments.add(this.modelMapper.map(commentDto, Comment.class));
//		post.setComments(comments);;
//		this.commentRepo.saveAll(comments);
//		return this.modelMapper.map(comments, CommentDto.class);
	}
	

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer postId) {
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment=this.commentRepo.findById(commentDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentDto.getId()));
		comment.setContent(commentDto.getContent());
		this.commentRepo.save(comment);
		return this.modelMapper.map(comment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		this.commentRepo.deleteById(commentId);
	}


}
