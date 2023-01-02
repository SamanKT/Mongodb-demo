package com.mongodb.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.mongodb.model.Student;
import com.mongodb.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepository repository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Integer saveStudent(Student student) {
		
		return repository.save(student).getId();
	}

	@Override
	public Student getStudentByName(String name) {
		
		return repository.findByNameStartsWith(name);
	}

	@Override
	public void delete(int id) {
		repository.deleteById(id);
		
	}

	@Override
	public List<Student> getByIdIntervals(int min, int max) {
		
		return repository.findByAgeBetweenCustom(min, max);
	}

	@Override
	public Page<Student> getAllBySearch(Integer id, String name, String address, String hobby, Pageable pageable) {
		
		Query query = new Query().with(pageable);
		List<Criteria> criterion = new ArrayList<>();
		
		if (id != null ) criterion.add(Criteria.where("id").is(id));
		if(name!=null && !name.isBlank()) criterion.add(Criteria.where("name").regex(name, "i")); // i : insensitive for case
		if (address!=null && !address.isEmpty()) criterion.add(Criteria.where("addresses.address1").is(address));
		
		if(!criterion.isEmpty()) {
			
			query.addCriteria(new Criteria().andOperator(criterion)); // to pass a list of criteria to the addCriteria method we should use andOperator
		}
		
		
		
		return PageableExecutionUtils  				// more info: https://medium.com/javarevisited/spring-boot-mongodb-searching-and-pagination-1a6c1802024a
				.getPage(
						mongoTemplate.find(query,Student.class),
						pageable, 
						() -> mongoTemplate.count(query.skip(0).limit(0), Student.class) );
	}

	@Override
	public List<Document> getOldestByAddress() {

		UnwindOperation unwindOperation = Aggregation.unwind("addresses");   // to flatten the addresses collection
		
		SortOperation sortOperation = Aggregation.sort(Direction.DESC,"age");
		GroupOperation groupOperation = Aggregation.group("addresses.address1").first(Aggregation.ROOT).as("oldest"); // Aggregation.ROOT references to the root document with full fields
		
		ProjectionOperation projectionOperation = Aggregation.project()
				.andExpression("_id").as("common-address")
				.andInclude("oldest")
				.andExclude("_id");
		
		Aggregation aggregation = Aggregation.newAggregation(unwindOperation,sortOperation, groupOperation,projectionOperation);  // the order of the operations matter
		
		List<Document> documents = mongoTemplate
				.aggregate(aggregation, Student.class, Document.class).getMappedResults();
		
		return documents;
	}

	@Override
	public List<Document> getAggregateByAddressPopulation() {
		
		UnwindOperation unwindOperation = Aggregation.unwind("addresses");
		
		GroupOperation groupOperation = Aggregation.group("addresses.address1").count().as("population");
		
		ProjectionOperation projectionOperation 
						= Aggregation.project()
									 .andExpression("_id").as("address-line-1")
									 .andExpression("population").as("pop")
									 .andExclude("_id");
		Aggregation aggregation = Aggregation.newAggregation(unwindOperation,groupOperation,projectionOperation );
		
		
		
		return mongoTemplate.aggregate(aggregation, Student.class,Document.class).getMappedResults();
	}

}
