package com.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.model.Photo;

@Repository
public interface ImageRepository extends MongoRepository<Photo, Integer>{

	
	
}
