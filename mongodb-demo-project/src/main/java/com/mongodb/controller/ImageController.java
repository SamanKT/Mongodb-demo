package com.mongodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.model.Photo;
import com.mongodb.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {

	@Autowired
	private ImageService imageService;
	
	@PostMapping
	public ResponseEntity<Integer> savePhoto(@RequestParam("image") MultipartFile file) throws Exception {
		
		return new ResponseEntity<Integer>(imageService.saveImage(file),  HttpStatus.OK) ;
	}
	
	@GetMapping
	public ResponseEntity<List<Photo>> getAllfiles(){
		
		return ResponseEntity.ok().body( imageService.getAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
		
		
		Photo photo = imageService.getPhotoById(id);
		
		Resource resource = new ByteArrayResource(photo.getImage().getData())  ;
		
		 return ResponseEntity.ok()			//download handling
				 .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+photo.getFileName()+"\"" )
				 .contentType(MediaType.APPLICATION_OCTET_STREAM) 		// APPLICATION_OCTET_STREAM is used for the binary data type; since we are fetching the binary data from db
				 .body(resource);
	}
	
}
