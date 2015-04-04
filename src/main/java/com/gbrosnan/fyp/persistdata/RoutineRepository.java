package com.gbrosnan.fyp.persistdata;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gbrosnan.fyp.objects.Routine;

@Repository
public class RoutineRepository {

	@Autowired
	MongoTemplate mongoTemplate;
	
	
	public void createCollection(String collection) {
		if (!mongoTemplate.collectionExists(collection)) {
	      mongoTemplate.createCollection(collection);
	    }
    }
	
	public boolean isCreated(String collection) {
		if (mongoTemplate.collectionExists(collection)) {
		      return true;
		}
		return false;
	}
	
	public int getCollectionSize(String collection) {
		return (int)mongoTemplate.getCollection(collection).getCount();		
	}
	
	public void insert(Routine exercise, String collection) {
		exercise.setId(getCollectionSize(collection) + 1);
		mongoTemplate.insert(exercise, collection);
	}
	
	public Routine getLatestData(String collection) {
		int size = getCollectionSize(collection);
		return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(size)), Routine.class, collection);
	}
	
	public List<String> getCollectionsNames() {	
		return new ArrayList<String>(mongoTemplate.getCollectionNames());
	}
	
	
}
