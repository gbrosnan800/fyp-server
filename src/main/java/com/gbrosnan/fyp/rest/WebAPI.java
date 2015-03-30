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
import com.gbrosnan.fyp.objects.ExerciseInfo;
import com.gbrosnan.fyp.objects.ExerciseWebObject;
import com.gbrosnan.fyp.objects.ProcessedExercise;
import com.gbrosnan.fyp.objects.ExerciseRaw;
import com.gbrosnan.fyp.objects.Rep;
import com.gbrosnan.fyp.persistdata.*;
import com.gbrosnan.fyp.preprocess.AdjustArraySize;
import com.gbrosnan.fyp.preprocess.PeakDetect;
import com.gbrosnan.fyp.preprocess.PreProcessingHandler;
import com.gbrosnan.fyp.preprocess.RepCreator;


@RestController
@RequestMapping("/rest/data")
public class WebAPI {

	@Autowired
	MainMongoRepository mainMongoRepository;
	
	
    @RequestMapping(value = "/2", method = RequestMethod.GET)
    public String hello2() {
    	return "{\"test\":\"hello world 2\"}";
    }
    
    @RequestMapping(value = "/tempdir", method = RequestMethod.GET)
    public String getTepDir() {	    	
    	return System.getProperty("java.io.tmpdir");
    }
    
    @RequestMapping(value = "/catalina", method = RequestMethod.GET)
    public String getCatalina() {	    	
    	return System.getProperty("catalina.base");
    }
	
    @RequestMapping(value = "/collectionnames", method = RequestMethod.GET)
    public List<String> getCollectionsNames() {
    	return mainMongoRepository.getCollectionsNames();
    }
    
    @RequestMapping(value = "/{collectionName}/sets", method = RequestMethod.GET)
    public List<ExerciseRaw> getSetsFromCollection(@PathVariable String collectionName) {
    	return mainMongoRepository.getSetsFromCollection(collectionName);
    } 
    
    @RequestMapping(value = "/{collectionName}/setsinfo", method = RequestMethod.GET)
    public List<ExerciseInfo> getSetsInfoFromCollection(@PathVariable String collectionName) {
    	return mainMongoRepository.getSetsInfoFromCollection(collectionName);
    } 
    
    @RequestMapping(value = "/maximas", method = RequestMethod.POST)
    public List<Integer> getMaximas(@RequestBody ExerciseWebObject exerciseWebObject ) {	    	
    	return PeakDetect.discoverMaximas(exerciseWebObject.getAverages(), exerciseWebObject.getStart(), exerciseWebObject.getRange());
    }    
	
    @RequestMapping(value = "/filterflatpeaks", method = RequestMethod.POST)
    public List<Integer> filterFlatPeaks(@RequestBody ExerciseWebObject exerciseWebObject ) {	    	
    	return PeakDetect.filterOutFlatPeaks(exerciseWebObject.getAverages(), exerciseWebObject.getMaximas(), exerciseWebObject.getRange(), exerciseWebObject.getHeight());
    } 
    
    @RequestMapping(value = "/filterouterrange", method = RequestMethod.POST)
    public List<Integer> filterOuterRange(@RequestBody ExerciseWebObject exerciseWebObject ) {	    	
    	return PeakDetect.filterOuterRangePeaks(exerciseWebObject.getAverages(), exerciseWebObject.getMaximas(), exerciseWebObject.getHeight());
    } 
       
    @RequestMapping(value = "/reps", method = RequestMethod.POST)
    public ProcessedExercise getReps(@RequestBody ExerciseWebObject exerciseWebObject ) {	    	
    	
    	ProcessedExercise processedExercise = new ProcessedExercise();
    	List<Rep> reps =  RepCreator.createRepList(exerciseWebObject.getAverages(), exerciseWebObject.getMaximas());
    	List<Rep> normalizedReps = RepCreator.normalizeReps(reps);	
		for(Rep rep : normalizedReps) {	
			List<Double> adjustedSize = AdjustArraySize.adjustTo(200, rep.getSamples());
			rep.setSamples(adjustedSize);	
		}
		processedExercise.setExtractedReps(reps);
    	processedExercise.setNormalisedReps(RepCreator.normalizeReps(normalizedReps));
    	return processedExercise;
    } 
       
}
