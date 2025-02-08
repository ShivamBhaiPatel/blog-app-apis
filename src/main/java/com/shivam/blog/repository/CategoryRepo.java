package com.shivam.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivam.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
