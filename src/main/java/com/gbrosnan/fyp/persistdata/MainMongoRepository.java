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
	
	public List<String> getCollectionsNames() {	
		return new ArrayList<String>(mongoTemplate.getCollectionNames());
	}
	
	public List<ExerciseRaw> getSetsFromCollection(String collectionName) {	
		return new ArrayList<ExerciseRaw>(mongoTemplate.findAll(ExerciseRaw.class, collectionName));
	}	
	
	
	
}
