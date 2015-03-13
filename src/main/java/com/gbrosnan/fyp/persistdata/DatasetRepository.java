package com.gbrosnan.fyp.persistdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gbrosnan.fyp.objects.ExerciseRaw;

@Repository
public class DatasetRepository {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public void createCollection() {
		if (!mongoTemplate.collectionExists("dataset")) {
	      mongoTemplate.createCollection("dataset");
	    }
    }
	
	public void dropCollection() {
		if (mongoTemplate.collectionExists("dataset")) {
			mongoTemplate.dropCollection("dataset");
		}
	}
	
	public int getCollectionSize() {
		
		return (int)mongoTemplate.getCollection("dataset").getCount();		
	}
	
	public void insert(ExerciseRaw exercise) {
		
		exercise.setId(getCollectionSize() + 1);
		mongoTemplate.insert(exercise, "dataset");
	}
	
	public List<ExerciseRaw> getExerciseList(String exerice) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("exerciseName").is(exerice));		
		return mongoTemplate.find(query, ExerciseRaw.class, "dataset");	
	}
	
}
