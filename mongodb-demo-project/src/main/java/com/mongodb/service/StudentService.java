package com.mongodb.service;


import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mongodb.model.Student;


public interface StudentService {

	Integer saveStudent(Student student);

	Student getStudentByName(String name);

	void delete(int id);

	List<Student> getByIdIntervals(int min, int max);

	Page<Student> getAllBySearch(Integer id, String name, String address, String hobby, Pageable pageable);

	List<Document> getOldestByAddress();

	List<Document> getAggregateByAddressPopulation();
	
}
