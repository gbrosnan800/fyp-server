package com.gbrosnan.fyp.persistdata;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gbrosnan.fyp.objects.*;

@Repository
public class MainMongoRepository {

	@Autowired
	MongoTemplate mongoTemplate;
	
	
	public void createCollection(String collection) {
		if (!mongoTemplate.collectionExists(collection)) {
	      mongoTemplate.createCollection(collection);
	    }
    }
	
	public int getCollectionSize() {
		
		return (int)mongoTemplate.getCollection("dataset").getCount();		
	}
	
	public void insert(ExerciseRaw exercise, String collection) {
		
		exercise.setId(getCollectionSize() + 1);
		mongoTemplate.insert(exercise, collection);
	}
	
	
	public List<String> getCollectionsNames() {	
		return new ArrayList<String>(mongoTemplate.getCollectionNames());
	}
	
	public List<ExerciseRaw> getSetsFromCollection(String collectionName) {	
		return new ArrayList<ExerciseRaw>(mongoTemplate.findAll(ExerciseRaw.class, collectionName));
	}	
	
	
	
}
