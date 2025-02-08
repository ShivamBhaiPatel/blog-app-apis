package com.shivam.blog.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shivam.blog.entities.Category;
import com.shivam.blog.entities.Post;
import com.shivam.blog.entities.User;
import com.shivam.blog.exceptions.ResourceNotFoundException;
import com.shivam.blog.payloads.PostDto;
import com.shivam.blog.payloads.PostResponse;
import com.shivam.blog.repository.CategoryRepo;
import com.shivam.blog.repository.PostRepo;
import com.shivam.blog.repository.UserRepo;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto) {
		User user = this.userRepo.findById(postDto.getUser().getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", postDto.getUser().getId()));
		Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategory().getCategoryId()));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto) {
		Post post=this.postRepo.findById(postDto.getPostId()).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postDto.getPostId()));
		post.setPostTitle(postDto.getPostTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost=this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		this.postRepo.deleteById(postId);
	}
	

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost=this.postRepo.findAll(p);
		List<PostDto> postDto=pagePost.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=setPostResponse(pagePost, postDto);
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostByCategoryId(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> posts=this.postRepo.findByCategoryId(categoryId, p);
		List<PostDto> postDtos=posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return setPostResponse(posts, postDtos);
	}

	@Override
	public PostResponse getPostByUserId(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> posts=this.postRepo.findByUserId(userId, p);
		List<PostDto> postDtos=posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=setPostResponse(posts, postDtos);
		return postResponse;
	}

	@Override
	public PostResponse searchPostByTitle(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageRequest=PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> posts=this.postRepo.findByPostTitleContaining(keyword, pageRequest);
		List<PostDto> postDtos=posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=setPostResponse(posts, postDtos);
		
		return postResponse;
	}
	
	public PostResponse setPostResponse(Page<Post> pagePost, List<PostDto> postDtos) {
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setFirstPage(pagePost.isFirst());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}


}
