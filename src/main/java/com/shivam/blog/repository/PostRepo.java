package com.shivam.blog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shivam.blog.entities.Post;

public interface PostRepo extends JpaRepository<Post, Integer>{
	Page<Post> findByUserId(Integer userId, Pageable pageRequest);
	Page<Post> findByCategoryId(Integer categoryId, Pageable pageRequest);
	Page<Post> findByPostTitleContaining(String keyword, Pageable pageRequest);

}
