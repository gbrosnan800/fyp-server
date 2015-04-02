package com.gbrosnan.fyp.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gbrosnan.fyp.ann.ArtificalNeuralNetwork;
import com.gbrosnan.fyp.objects.ANN_ConfigData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/rest/ann")
public class RestANN {

	ArtificalNeuralNetwork ann;
	List<List<String>> testResult;
	
	@RequestMapping(value = "/status", method = RequestMethod.GET)
    public String isTrained() {
    	
		String status = "untrained";
		try {
			String test = ann.toString();
			status = "trained";
		}
		catch (NullPointerException ex) {
			status = "untrained";
		}
    	    	
    	return "{\"status\":\"" + status + "\"}";
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


}
