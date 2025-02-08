package com.shivam.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivam.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
