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
	
    @RequestMapping(value = "/train", method = RequestMethod.POST)
    public String trainNetwork(@RequestBody ANN_ConfigData annConfigData) {
    	
    	ann = new ArtificalNeuralNetwork();
    	ann.configNetwork(annConfigData);
    	ann.trainNetwork();
    	List<List<String>> testResult = ann.testClassificationOfExercises();
    	    	
    	return new Gson().toJson(testResult);
    }
    

	

}
