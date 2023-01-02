package com.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "datasequence-document")    // since in the mongodb we do not have @GeneratedValue for the auto increment we need to implement it manually
													// this document holds the sequence needed for the auto increment process
public class Datasequence_holder {

	@Id
	private String id;

	private int seq;

	public Datasequence_holder() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

}
