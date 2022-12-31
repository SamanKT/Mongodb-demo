package com.mongodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.model.Student;
import com.mongodb.service.StudentService;

@RestController
@RequestMapping("/")
public class StudentController {

	@Autowired
	private StudentService service;

	@PostMapping
	public ResponseEntity<Object> saveHandler(@RequestBody Student student) {

		return new ResponseEntity<Object>(service.saveStudent(student), HttpStatus.ACCEPTED);

	}

	@GetMapping
	public ResponseEntity<Object> getStudent(@RequestParam("name") String name) {

		return new ResponseEntity<Object>(service.getStudentByName(name), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable() int id) {

		service.delete(id);
	}

	@GetMapping("/age")
	public ResponseEntity<Object> getByAge(@RequestParam("min") int min, @RequestParam("max") int max) {

		return new ResponseEntity<Object>(service.getByIdIntervals(min, max), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<Page<Student>> getAllSearchByPagination(
			@RequestParam(required = false) Integer id,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String address,
			@RequestParam(required = false) String hobby,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size
			) {
		Pageable pageable = PageRequest.of(page!=null ? page:0 , size!=null ? size:10);  // show the first page if the page number is not specified AND put 10 documents in one page
		
		return new ResponseEntity<Page<Student>>(service.getAllBySearch(id,name,address,hobby,pageable), HttpStatus.OK);
		
	}

}
