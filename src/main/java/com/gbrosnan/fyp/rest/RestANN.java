package com.gbrosnan.fyp.rest;


import java.util.List;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gbrosnan.fyp.ann.ArtificalNeuralNetwork;
import com.gbrosnan.fyp.objects.ANN_ConfigData;
import com.gbrosnan.fyp.objects.ExerciseRaw;
import com.gbrosnan.fyp.objects.ProcessedExercise;
import com.gbrosnan.fyp.objects.Rep;
import com.gbrosnan.fyp.objects.Routine;
import com.gbrosnan.fyp.persistdata.RoutineRepository;
import com.gbrosnan.fyp.preprocess.PreProcessingHandler;
import com.google.gson.Gson;

@RestController
@RequestMapping("/rest/ann")
public class RestANN {

	@Autowired
	RoutineRepository routineRepository;
	
	ArtificalNeuralNetwork ann;
	List<List<String>> testResult;
	
	@RequestMapping(value = "/status", method = RequestMethod.GET)
    public String checkStatus() {
    	
		String status = "untrained";
		if(isTrained()) {
			status = "trained";
		}    	    
    	return "{\"status\":\"" + status + "\"}";
	}
	
	private boolean isTrained() {
		try {
			String test = ann.toString();
			return true;
		}
		catch (NullPointerException ex) {
			return false;
		}    	   
	}
	
	
	@RequestMapping(value = "/train", method = RequestMethod.POST)
    public String trainNetwork(@RequestBody ANN_ConfigData annConfigData) {
    	
    	ann = new ArtificalNeuralNetwork();
    	ann.configNetwork(annConfigData);
    	ann.trainNetwork();
    	testResult = ann.testClassificationOfExercises();
    	    	
    	return new Gson().toJson(testResult);
    }
    
	@RequestMapping(value = "/results", method = RequestMethod.GET)
    public String getResults() {
    	    	    	
    	return new Gson().toJson(testResult);
    }
	
    @RequestMapping(value = "/detect", method = RequestMethod.POST)
    public String detectExercise(HttpEntity<byte[]> requestEntity) {
    		    	
    	ExerciseRaw exerciseRaw = null;
    	ProcessedExercise processedExercise = null;
    	String recognizedExercise = "ANN not trained";
    	int repCount = 0;

        try {   		
        	if(isTrained()) {        	
            	byte[] jsonStringBytes = requestEntity.getBody();
            	String jsonString = new String(jsonStringBytes);
            	Gson gson = new Gson();
            	exerciseRaw = gson.fromJson(jsonString, ExerciseRaw.class); 
            	
            	PreProcessingHandler handler = new PreProcessingHandler();
            	//processedExercise = handler.preprocessRawExerciseForANN(exerciseRaw);
            	processedExercise = handler.preprocessRawExerciseForANN3Axis(exerciseRaw);
            	recognizedExercise = detectExercise(processedExercise);            	
            	repCount = processedExercise.getExtractedReps().size();
        	}      	
        	return "{\"status\":\"ok\",\"exercise\":\"" + recognizedExercise + "\",\"reps\":" + repCount  + "}";
        }
        catch(Exception ex) {
        	System.out.println(ex.toString());
        	return "Server error: " + ex.toString();
        }
    }
    
    @RequestMapping(value = "/rmtest/{collection}", method = RequestMethod.POST)
    public String rmTest(HttpEntity<byte[]> requestEntity, @PathVariable String collection) {
		  			
	    try {
	    	if(!isTrained()) {
	    		return "{\"status\":\"error\",\"server_error\":\"Server Error: Network is not trained\"}";
	    	}
	    	if(routineRepository.isCreated(collection)) {
	    		return "{\"status\":\"error\",\"server_error\":\"Collection already exists!\"}";
	    	}
	    	
    		ExerciseRaw exerciseRaw = transferData(requestEntity);
    		PreProcessingHandler handler = new PreProcessingHandler();	    		
    		ProcessedExercise exerciseProcessed = handler.preprocessRawExerciseForANN3Axis(exerciseRaw);    		
    		String detectedExercise = detectExercise(exerciseProcessed);	    		
    		exerciseProcessed.setExerciseDetected(detectedExercise);
    		
    		Routine routine = handler.createNewRoutine(exerciseProcessed, collection);
    		routineRepository.createCollection(collection);
    		routineRepository.insert(routine, collection);  	    		
	    	return routine.toJsonString("");    	    	
	    }
	    catch(Exception ex) {
	    	return "{\"status\":\"error\",\"Server Error\":\"" + ex.toString() + "\"}";
	    }
    }
    
    @RequestMapping(value = "/routine/{collection}", method = RequestMethod.POST)
    public String routineExercise(HttpEntity<byte[]> requestEntity, @PathVariable String collection) {
		
	    try {
	    	if(!isTrained()) {
	    		return "{\"status\":\"error\",\"server_error\":\"Server Error: Network is not trained\"}";
	    	}	    
	    	if(!routineRepository.isCreated(collection)) {
	    		return "{\"status\":\"error\",\"server_error\":\"Collection doesn't exist - Do 1RM test first\"}";
	    	}
    		ExerciseRaw exerciseRaw = transferData(requestEntity);
    		PreProcessingHandler handler = new PreProcessingHandler();	    		
    		ProcessedExercise exerciseProcessed = handler.preprocessRawExerciseForANN3Axis(exerciseRaw);    		
    		String detectedExercise = detectExercise(exerciseProcessed);	    		
    		exerciseProcessed.setExerciseDetected(detectedExercise);       		

    		Routine currentRoutine = routineRepository.getLatestData(collection);
    		Routine updatedRoutine = handler.updateRoutine(currentRoutine, exerciseProcessed);
    		routineRepository.insert(updatedRoutine, collection);  
    		
    		String differenceFromPrevious1RM = getDifferenceFromPrevious1RM(updatedRoutine.getCurrent1RM() - currentRoutine.getCurrent1RM());
	    	return updatedRoutine.toJsonString(differenceFromPrevious1RM);
	    }
	    catch(Exception ex) {
	    	return "{\"status\":\"error\",\"server_error\":\"Server Error: " + ex.toString() + "\"}";
	    }
    
    }
    
    private String getDifferenceFromPrevious1RM(int difference) {
		if(difference > 0) {
			return "(+" + difference + "kg)";
		}
		else if(difference < 0) {
			return "(-" + difference + "kg)";
		} 
		else {
			return "(No change)";
		}
    }
    
    @RequestMapping(value = "/routine/{collection}", method = RequestMethod.GET)
    public String getRroutineInfo(@PathVariable String collection) {
   	    		
	    try { 
	    	if(!routineRepository.isCreated(collection)) {
	    		return "{\"status\":\"error\",\"server_error\":\"Collection not found\"}";
	    	}
	    	Routine routine = routineRepository.getLatestData(collection);
	    	return routine.toJsonString("");
	    }
	    catch(Exception ex) {
	    	return "{\"status\":\"error\",\"server_error\":\"Server Error: " + ex.toString() + "\"}";
	    }
    
    }
        
    private ExerciseRaw transferData(HttpEntity<byte[]> requestEntity) {
		byte[] jsonStringBytes = requestEntity.getBody();
    	String jsonString = new String(jsonStringBytes);
    	Gson gson = new Gson();
    	return gson.fromJson(jsonString, ExerciseRaw.class); 
    }
    
    private String detectExercise(ProcessedExercise processedExercise) {
    	
    	DataSet dataset = new DataSet(600);
    	DataSetRow row; 
    	
    	for(Rep rep : processedExercise.getNormalisedReps()) {
    		
    		List<Double> pointsList = rep.getSamples();
    		
    		double[] points = new double[pointsList.size()];
    		for(int i = 0 ; i < 600; i ++ ) {
    			points[i] = pointsList.get(i);
    		}        		
    		row = new DataSetRow(points);
    		dataset.addRow(row);
    	}       
    	return ann.recognizeExercise(dataset);         	
    }


}
