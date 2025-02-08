package com.shivam.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivam.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
