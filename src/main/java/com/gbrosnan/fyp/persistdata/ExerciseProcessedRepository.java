package com.gbrosnan.fyp.persistdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.gbrosnan.fyp.objects.ProcessedExercise;

@Repository
public class ExerciseProcessedRepository {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public void createCollection() {
		if (!mongoTemplate.collectionExists(ProcessedExercise.class)) {
	      mongoTemplate.createCollection(ProcessedExercise.class);
	    }
    }
	
	public void dropCollection() {
		if (mongoTemplate.collectionExists(ProcessedExercise.class)) {
			mongoTemplate.dropCollection(ProcessedExercise.class);
		}
	}
	
	public int getCollectionSize() {
			
		return (int)mongoTemplate.getCollection("exercises").getCount();		
	}
	
	public void insert(ProcessedExercise exercise) {
		
		exercise.setId(getCollectionSize() + 1);
		mongoTemplate.insert(exercise, "exercises");
	}
}
