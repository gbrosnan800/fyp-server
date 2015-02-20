package com.gbrosnan.fyp.persistdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.gbrosnan.fyp.objects.ExerciseRaw;

@Repository
public class ExerciseRawRepository {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public void createCollection() {
		if (!mongoTemplate.collectionExists(ExerciseRaw.class)) {
	      mongoTemplate.createCollection(ExerciseRaw.class);
	    }
    }
	
	public void dropCollection() {
		if (mongoTemplate.collectionExists(ExerciseRaw.class)) {
			mongoTemplate.dropCollection(ExerciseRaw.class);
			System.out.println("collection dropped");
		}
	}
	
	public int getCollectionSize() {
			
		return (int)mongoTemplate.getCollection("exercises").getCount();		
	}
	
	public void insert(ExerciseRaw exercise) {
		
		exercise.setId(getCollectionSize() + 1);
		mongoTemplate.insert(exercise, "exercises");
	}
	
	
}
