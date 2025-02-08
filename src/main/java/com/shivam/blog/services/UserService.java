package com.shivam.blog.services;

import java.util.List;

import com.shivam.blog.exceptions.ResourceNotFoundException;
import com.shivam.blog.payloads.UserDto;

public interface UserService {
	
	UserDto addUser(UserDto user);
	UserDto updateUser(UserDto user);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);

}
