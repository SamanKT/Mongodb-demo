package com.mongodb.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.model.Datasequence_holder;

@Service
public class IdSequenceService {

	@Autowired
	private MongoOperations mongoOperations;
	
	public Integer generateSequence(String seqName) {
		
		Datasequence_holder sequence = mongoOperations.findAndModify(
				Query.query(Criteria.where("_id").is(seqName))
				, new Update().inc("seq", 1)
				, FindAndModifyOptions.options().returnNew(true).upsert(true)
				, Datasequence_holder.class
				);
		return Objects.isNull(sequence)? 1: sequence.getSeq();
	}
	
	
}
