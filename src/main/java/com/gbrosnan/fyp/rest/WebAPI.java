package com.gbrosnan.fyp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.gbrosnan.fyp.objects.AverageList;
import com.gbrosnan.fyp.objects.ProcessedExercise;
import com.gbrosnan.fyp.objects.ExerciseRaw;
import com.gbrosnan.fyp.persistdata.*;
import com.gbrosnan.fyp.preprocess.PeakDetect;
import com.gbrosnan.fyp.preprocess.PreProcessingHandler;


@RestController
@RequestMapping("/rest/data")
public class WebAPI {

	@Autowired
	MainMongoRepository mainMongoRepository;
	
	
    @RequestMapping(value = "/2", method = RequestMethod.GET)
    public String hello2() {
    	return "{\"test\":\"hello world 2\"}";
    }
	
    @RequestMapping(value = "/collectionnames", method = RequestMethod.GET)
    public List<String> getCollectionsNames() {
    	return mainMongoRepository.getCollectionsNames();
    }
    
    @RequestMapping(value = "/{collectionName}/sets", method = RequestMethod.GET)
    public List<ExerciseRaw> getSetsFromCollection(@PathVariable String collectionName) {
    	return mainMongoRepository.getSetsFromCollection(collectionName);
    } 
    
    @RequestMapping(value = "/maximas", method = RequestMethod.POST)
    public List<Integer> getMaximas(@RequestBody AverageList averageList ) {	    	
    	return PeakDetect.discoverMaximas(averageList.getAverages(), averageList.getStart(), averageList.getRange());
    }    
	
    
}
