package com.shivam.blog.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shivam.blog.payloads.FileResponse;
import com.shivam.blog.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/file")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@Value("project.image")
	private String path;
	
	@PostMapping("/post/image/upload/")
	public ResponseEntity<FileResponse> uploadFile(@RequestParam MultipartFile image) {
		String fileName = this.fileService.uploadImage(path, image);
		return new ResponseEntity<FileResponse>(new FileResponse(fileName, "image is sucessfully uploaded."),
				HttpStatus.CREATED);
	}
	
	@GetMapping(value="/images/", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@RequestParam String imageName, HttpServletResponse response){
		// http://localhost:9090/api/images/abc.png
		try {
			InputStream resource = this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		} catch (IOException  e) {
			e.printStackTrace();
		}
	}

}
