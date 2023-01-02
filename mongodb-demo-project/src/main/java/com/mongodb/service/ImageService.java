package com.mongodb.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.model.Photo;



public interface ImageService {

	Integer saveImage(MultipartFile file) throws Exception;

	Photo getPhotoById(Integer id);

	List<Photo> getAll();

}
