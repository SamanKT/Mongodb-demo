package com.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.model.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, Integer> {

	Student findByNameStartsWith(String name);
	
	//List<Student> findByAgeBetween(int min, int max); // this is a supported query method for more info: https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html

	@Query(value = "{'age': {$gt: ?0, $lt: ?1} }"
			// ?0: the first passed parameter of the method   $gt: grater than   $lt: less than
            // more info: https://www.baeldung.com/queries-in-spring-data-mongodb
			, fields = "{hobbies:0}"  //0: means no include    1: include in the return
			)    
	List<Student> findByAgeBetweenCustom(Integer min, Integer max);
	
}
