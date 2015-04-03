package com.gbrosnan.fyp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.gbrosnan.fyp.objects.ProcessedExercise;
import com.gbrosnan.fyp.objects.ExerciseRaw;
import com.gbrosnan.fyp.objects.StatusMessage;
import com.gbrosnan.fyp.persistdata.*;
import com.gbrosnan.fyp.preprocess.PreProcessingHandler;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/rest")
public class RestAPI {	
	
	@Autowired
	DatasetRepository datasetRepository;
	
	@Autowired
	MainMongoRepository mainMongoRepository;
	
	@Autowired
	ExerciseProcessedRepository exerciseProcessedRepository;
	
	@Autowired
	PreProcessingHandler preprocessingHandler;
	
	@Autowired
	ExerciseProcessedToExcel exerciseProcessedToExcel;
	
	@Autowired
	ExerciseProcessedToCSV exerciseProcessedToCSV;
	
	@RequestMapping(value = "/1", method = RequestMethod.GET)
    public String hello1() {
    	return "hello world 1";
    }
    
    @RequestMapping(value = "/2", method = RequestMethod.GET)
    public String hello2() {
    	return "hello world 2";
    }
    
    @RequestMapping(value = "/exercise", method = RequestMethod.POST)
    public String handleFileUpload(HttpEntity<byte[]> requestEntity) {
    		
    	ExerciseRaw exerciseRaw = null;
    	
        try {
        	byte[] jsonStringBytes = requestEntity.getBody();
        	String jsonString = new String(jsonStringBytes);
        	Gson gson = new Gson();
        	exerciseRaw = gson.fromJson(jsonString, ExerciseRaw.class);
        	System.out.println(exerciseRaw.getExerciseName());

        	
        	//return "Server successfully saved Exercise";
        }
        catch(Exception ex) {
        	//return "Server error: " + ex.toString();
        }
        
    	
    	ProcessedExercise processedExercise = preprocessingHandler.preprocessRawExerciseForANN(exerciseRaw);
    	
    	exerciseProcessedRepository.insert(processedExercise);
    	exerciseProcessedToExcel.createExcelFile(processedExercise);
    	exerciseProcessedToCSV.createCSVFile(processedExercise.getNormalisedReps(), processedExercise.getId(), processedExercise.getExerciseName());
    	return "done";
    }
    
    @RequestMapping(value = "/datasetitem/{collection}", method = RequestMethod.POST)
    public String uploadDataSetItem(HttpEntity<byte[]> requestEntity, @PathVariable String collection) {
    		
    	ExerciseRaw exerciseRaw = null;    	
        try {
        	byte[] jsonStringBytes = requestEntity.getBody();
        	String jsonString = new String(jsonStringBytes);
        	Gson gson = new Gson();
        	exerciseRaw = gson.fromJson(jsonString, ExerciseRaw.class);    
        	mainMongoRepository.createCollection(collection);
        	mainMongoRepository.insert(exerciseRaw, collection);       
        	return "Exercise received by server and added to database";
        }
        catch(Exception ex) {
        	return "Server error: " + ex.toString();
        }
    }  
    
    @RequestMapping(value = "/query/{exercise}", method = RequestMethod.GET)
    public String testQ(@PathVariable String exercise) {
    		
    	List<ExerciseRaw> exercises = datasetRepository.getExerciseList(exercise);
    	
    	//int size = datasetRepository.getCollectionSize();
    	
    	return "Exercise: " + exercise + "    size: " + exercises.size(); 
    }
    
    @RequestMapping(value = "/rmtest/{collection}", method = RequestMethod.POST)
    public String rmTest(HttpEntity<byte[]> requestEntity, @PathVariable String collection, HttpServletRequest request) {
		
		ExerciseRaw exerciseRaw = null;    			
	    try {
	    	if(!isTrained(request)) {
	    		return "{\"status\":\"error\",\"server_error\":\"Server Error: Network is not trained\"}";
	    	}

	    	// CHECK IF TRAINED - if not send error message 
	    	// DETECT EXERCISE = return preprocessed exercise with name of exercise, test_user, reps etc,  NEED MAXIMA & MINIMA LIST!
	    	// -------  DATA WILL NEED TO BE ADDED TO EXERCISE OBJECT FOR WEB PAGE ANALYSIS = MAXIMAS & MINIMA LIST
	    	// CALC SPEED OF LAST REP
	    	// CALC FORMULA FOR 1RM
	    	// MARSHAL DATA TO RM-OBJECT - SEND TO MONGO, SENT TO ANDROID
	    	
	    	// FOR ROUTINE - MUST CHECK IF TRAINED AND ALSO IF COLLECTION EXISTS, THEN GET LATEST DATA AND COMPARE TO EXERCISE
	    	
	    	
	    	byte[] jsonStringBytes = requestEntity.getBody();
	    	String jsonString = new String(jsonStringBytes);
	    	Gson gson = new Gson();
	    	exerciseRaw = gson.fromJson(jsonString, ExerciseRaw.class); 
	    	
	    	// Detect and get Processed EX
	    	// Calc Speed + 1RM + next session weigh
	    	
	    	// return 
	    	
	    	/*
	    	mainMongoRepository.createCollection(collection);
	    	mainMongoRepository.insert(exerciseRaw, collection);      
	    	*/
	    	
	    	return "{\"status\":\"ok\",\"rm\":\"13\",\"difference\":\"+10\",\"next_weight\":\"19\"}";
	    }
	    catch(Exception ex) {
	    	return "{\"status\":\"error\",\"Server Error\":\"" + ex.toString() + "\"}";
	    }
    }
    
    @RequestMapping(value = "/routine/{collection}", method = RequestMethod.POST)
    public String routineExercise(HttpEntity<byte[]> requestEntity, @PathVariable String collection, HttpServletRequest request) {
		
		ExerciseRaw exerciseRaw = null;    	
	    try {
	    	if(!isTrained(request)) {
	    		return "{\"status\":\"error\",\"server_error\":\"Server Error: Network is not trained\"}";
	    	}	    	
	    	
	    	byte[] jsonStringBytes = requestEntity.getBody();
	    	String jsonString = new String(jsonStringBytes);
	    	Gson gson = new Gson();
	    	exerciseRaw = gson.fromJson(jsonString, ExerciseRaw.class);   
	    	/*
	    	mainMongoRepository.createCollection(collection);
	    	mainMongoRepository.insert(exerciseRaw, collection);
	    	*/       
	    	return "{\"status\":\"ok\",\"rm\":\"13\",\"difference\":\"+10\",\"next_weight\":\"19\"}";
	    }
	    catch(Exception ex) {
	    	return "{\"status\":\"error\",\"server_error\":\"Server Error: " + ex.toString() + "\"}";
	    }
    
    }
    
    @RequestMapping(value = "/routine/{collection}", method = RequestMethod.GET)
    public String getRroutineInfo(@PathVariable String collection) {
   	
	    try { 


	    	
	    	
	    	
	    	// CHECK IF REP EXISTS - IF NOT SEND BACK ERROR MESSAGE
	    	// GET LATEST RM - DIFFERENCE = NULL SINCE ITS JUST A REQUEST
	    	// GET THE NEXT WEIGHT TO DO
	    	// SEND DATA	    	    	    	
	    	/*
	    	mainMongoRepository.createCollection(collection);
	    	mainMongoRepository.insert(exerciseRaw, collection);
	    	*/       
	    	return "{\"status\":\"ok\",\"rm\":\"13\",\"difference\":\"+10\",\"next_weight\":\"19\"}";
	    }
	    catch(Exception ex) {
	    	return "{\"status\":\"error\",\"server_error\":\"Server Error: " + ex.toString() + "\"}";
	    }
    
    }
    
    
    private boolean isTrained(HttpServletRequest request) {
    	
    	String hostname = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    	RestTemplate restTemplate = new RestTemplate();
    	StatusMessage message = restTemplate.getForObject(hostname + "/rest/ann/status",StatusMessage.class);

    	if(message.getStatus().equals("trained")) {
    		return true;
    	}
    	else {
        	return false;    		
    	}  	
    }
}
