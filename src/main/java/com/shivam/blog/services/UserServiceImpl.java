package com.shivam.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shivam.blog.entities.User;
import com.shivam.blog.exceptions.ResourceNotFoundException;
import com.shivam.blog.payloads.UserDto;
import com.shivam.blog.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto addUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto){
		User user=this.userRepo.findById(userDto.getId()).orElseThrow(() ->  new  ResourceNotFoundException( "User", "Id", userDto.getId()));
		user.setName(userDto.getName());
		user.setEmailId(userDto.getEmailId());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(() ->  new ResourceNotFoundException( "User", "Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users=this.userRepo.findAll();
		List<UserDto> userDto=  users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		this.userRepo.findById(userId).orElseThrow(() ->  new ResourceNotFoundException( "User", "Id", userId));
		this.userRepo.deleteById(userId);
	}
	
	
	public User dtoToUser(UserDto userDto) {
		return this.modelMapper.map(userDto, User.class);
	}
	
	public UserDto userToDto(User user) {
		return this.modelMapper.map(user, UserDto.class);
	}

}
