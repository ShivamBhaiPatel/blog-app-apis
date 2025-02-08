package com.shivam.blog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadTest {

	public static void main(String[] args) {
		String path="\"C:\\Users\\patel\\OneDrive\\Pictures\\premium_photo.jpg\"";
		
//		uploadImage(path, file);
	}
	
	public String uploadImage(String path, MultipartFile file) throws IOException {
		String name = file.getOriginalFilename();

		// random name generate file
		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

		// fullpath
		String filePath = path + File.separator + fileName;
		
		// create folder if not present
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return name;
	}
	
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
