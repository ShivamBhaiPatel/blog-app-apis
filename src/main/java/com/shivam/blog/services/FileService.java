package com.shivam.blog.services;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	String uploadImage(String path, MultipartFile file);
	InputStream getResource(String path, String fileName);

}
