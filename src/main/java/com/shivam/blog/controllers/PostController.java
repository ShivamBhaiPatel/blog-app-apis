package com.shivam.blog.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shivam.blog.config.AppConstansts;
import com.shivam.blog.payloads.ApiResponse;
import com.shivam.blog.payloads.PostDto;
import com.shivam.blog.payloads.PostPayload;
import com.shivam.blog.payloads.PostResponse;
import com.shivam.blog.services.FileService;
import com.shivam.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	
	@Value("project.image")
	private String path;
	
	@PostMapping("/")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
		PostDto createdPost=this.postService.createPost(postDto);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
//	@PostMapping(value="/", consumes = MediaType.ALL_VALUE)
//	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @RequestParam MultipartFile image){
//		String fileName=this.fileService.uploadImage(path, image);
//		postDto.setImageName(fileName);
//		PostDto createdPost=this.postService.createPost(postDto);
//		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
//	}
	
//	@PostMapping(value="/", consumes = MediaType.ALL_VALUE)
//	public ResponseEntity<PostPayload> createPost(@RequestBody PostPayload postPayload){
//		PostDto postDto=postPayload.getPostDto();
//		MultipartFile image=postPayload.getFile();
//		String fileName=this.fileService.uploadImage(path, image);
//		postDto.setImageName(fileName);
//		PostDto createdPost=this.postService.createPost(postDto);
//		postPayload.setPostDto(createdPost);
//		return new ResponseEntity<PostPayload>(postPayload, HttpStatus.CREATED);
//	}
	
	@PutMapping("/")
//	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @RequestParam MultipartFile image){
//		String fileName=this.fileService.uploadImage(path, image);
//		postDto.setImageName(fileName);
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto){
		PostDto updatedPost=this.postService.updatePost(postDto);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto=this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(defaultValue = AppConstansts.PAGE_NUMBER) Integer pageNumber, 
			@RequestParam(defaultValue = AppConstansts.PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = AppConstansts.POST_SORT_BY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstansts.SORT_DIR,required=false) String sortDir){
		PostResponse posts=this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post has been deleted successfully.", true), HttpStatus.OK);
	}
	@GetMapping("/search/{keyword}")
	public ResponseEntity<PostResponse> getPostByKeyword(@PathVariable String keyword, 
			@RequestParam(defaultValue = AppConstansts.PAGE_NUMBER) Integer pageNumber, 
			@RequestParam(defaultValue = AppConstansts.PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = AppConstansts.POST_SORT_BY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstansts.SORT_DIR,required=false) String sortDir){
		PostResponse postDto=this.postService.searchPostByTitle(keyword, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postDto, HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId, 
			@RequestParam(defaultValue = AppConstansts.PAGE_NUMBER) Integer pageNumber, 
			@RequestParam(defaultValue = AppConstansts.PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = AppConstansts.POST_SORT_BY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstansts.SORT_DIR,required=false) String sortDir){
		PostResponse postResponse=this.postService.getPostByCategoryId(categoryId, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId, 
			@RequestParam(defaultValue = AppConstansts.PAGE_NUMBER) Integer pageNumber, 
			@RequestParam(defaultValue = AppConstansts.PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = AppConstansts.POST_SORT_BY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstansts.SORT_DIR,required=false) String sortDir){
		PostResponse postResponse=this.postService.getPostByUserId(userId, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	@PostMapping("/image/upload")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam MultipartFile image, @RequestParam Integer postId){
		
		PostDto postDto=this.postService.getPostById(postId);
		String fileName=this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		this.postService.updatePost(postDto);
		
//		return new ResponseEntity<FileResponse>(new FileResponse(fileName, "image is sucessfully uploaded."),
//				HttpStatus.CREATED);
		PostDto updatedPost=this.postService.updatePost(postDto);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	@GetMapping(value="/post/image/", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@RequestParam String imageName, HttpServletResponse response){
		try {
			InputStream resource = this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		} catch (IOException  e) {
			e.printStackTrace();
		}
	}

}
