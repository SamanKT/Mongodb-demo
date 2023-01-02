package com.mongodb.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection = "photo-collection")   //the collection word can be set as collation by mistake in auto completion
@JsonInclude(value = Include.NON_NULL)
public class Photo {

	@Id
	private int id;

	@Transient  	// to exclude it from document    it works as a key for sequence incrementing
	public static final String SEQ_NAME = "data_sequence";
	
	private String fileName;
	
	private Binary image;

	public Photo() {
		
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Binary getImage() {
		return image;
	}

	public void setImage(Binary image) {
		this.image = image;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
