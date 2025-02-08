package com.shivam.blog.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) {
		String name = file.getOriginalFilename();
		
		// random name generate file
		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

		// fullpath
		String filePath = path + File.separator + fileName;
		
		// create folder if not present
		File f=new File(path);
		if(!f.exists())
			f.mkdir();
		
		//file copy
		try {
			Files.copy(file.getInputStream(), Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName){
		
		String fullPath= path+File.separator+fileName;
		InputStream is = null;
		try {
			is = new FileInputStream(fullPath);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return is;
		
	}

}
