package com.mongodb.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mongodb.model.Student;


public interface StudentService {

	Integer saveStudent(Student student);

	Student getStudentByName(String name);

	void delete(int id);

	List<Student> getByIdIntervals(int minAge, int maxAge);

	Page<Student> getAllBySearch(Integer id, String name, String address, String hobby, Pageable pageable);
	
}
