package com.mongodb.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.model.Photo;
import com.mongodb.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private IdSequenceService seqService;
	
	@Override
	public Integer saveImage(MultipartFile file) throws Exception {
		
		Photo photo = new Photo();
		photo.setId(seqService.generateSequence(Photo.SEQ_NAME));
		photo.setImage(new Binary(file.getBytes()));
		photo.setFileName(file.getOriginalFilename());
		return imageRepository.save(photo).getId();
	}

	@Override
	public Photo getPhotoById(Integer id) {
		return imageRepository.findById(id).get();
		
	}

	@Override
	public List<Photo> getAll() {
		
		
		return imageRepository.findAll();
	}

}
