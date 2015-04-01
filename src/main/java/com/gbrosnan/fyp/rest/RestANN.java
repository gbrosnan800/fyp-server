package com.gbrosnan.fyp.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gbrosnan.fyp.objects.ANN_DataConfig;

@RestController
@RequestMapping("/rest/ann")
public class RestANN {

	
    @RequestMapping(value = "/train", method = RequestMethod.POST)
    public String createCSVFile(@RequestBody ANN_DataConfig annDataConfig) {
    	
    	
    	System.out.println(annDataConfig.getTrainingFileName());
    	System.out.println(annDataConfig.getTestingFileName());
    	System.out.println(annDataConfig.getInputPerceptrons());
    	System.out.println(annDataConfig.getHiddenPerceptrons());
    	System.out.println(annDataConfig.getOutputPerceptrons());
    	System.out.println(annDataConfig.getLearningRate());
    	System.out.println(annDataConfig.getMomentum());
    	System.out.println(annDataConfig.getMaxIterations());

    	
    	
    	return "{\"message\":\"ok\"}";
    }
	
	
	/*
	 
	 			//File directoryPath = new File(System.getProperty("catalina.base") + "/webapps/fyp-server/csv");
			File directoryPath = new File(System.getProperty("catalina.base") + "\\wtpwebapps\\fyp-server\\csv");
			
			
			*/
}
