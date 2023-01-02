package com.mongodb.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection = "student")
@JsonInclude(value = Include.NON_NULL)
@JsonFilter("myFilter")  // add dynamic filter which is defined at {@link StudentController}
public class Student {

	@Id
	private int id;
	
	private Integer age;
	
	private String name;
	
	private List<StrudentAddress> addresses;
	
	private List<String> hobbies;

	public Student() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StrudentAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<StrudentAddress> addresses) {
		this.addresses = addresses;
	}

	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}
