package com.gbrosnan.fyp.rest;

import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gbrosnan.fyp.ann.ArtificalNeuralNetwork;
import com.gbrosnan.fyp.objects.ANN_ConfigData;
import com.gbrosnan.fyp.objects.ExerciseRaw;
import com.gbrosnan.fyp.objects.ProcessedExercise;
import com.gbrosnan.fyp.objects.Rep;
import com.gbrosnan.fyp.preprocess.PreProcessingHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/rest/ann")
public class RestANN {

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
            	recognizedExercise = ann.recognizeExercise(dataset);            	
            	repCount = processedExercise.getExtractedReps().size();
        	}      	
        	return "{\"status\":\"ok\",\"exercise\":\"" + recognizedExercise + "\",\"reps\":" + repCount  + "}";
        }
        catch(Exception ex) {
        	System.out.println(ex.toString());
        	return "Server error: " + ex.toString();
        }
    }
	
	


}
