package com.gbrosnan.fyp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.gbrosnan.fyp.ann.PreprocessExercise;
import com.gbrosnan.fyp.objects.ProcessedExercise;
import com.gbrosnan.fyp.objects.ExerciseRaw;
import com.gbrosnan.fyp.persistdata.ExerciseProcessedRepository;
import com.gbrosnan.fyp.persistdata.ExerciseProcessedToCSV;
import com.gbrosnan.fyp.persistdata.ExerciseProcessedToExcel;
import com.gbrosnan.fyp.preprocess.PreProcessingHandler;


@RestController
@RequestMapping("/rest")
public class RestAPI {	
	
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
       
        try {
        	byte[] jsonStringBytes = requestEntity.getBody();
        	String jsonString = new String(jsonStringBytes);
        	Gson gson = new Gson();
        	ExerciseRaw exerciseRaw = gson.fromJson(jsonString, ExerciseRaw.class);
        	
        	
        	ProcessedExercise processedExercise = preprocessingHandler.preprocessRawExerciseForANN(exerciseRaw);
        	processedExercise.setExerciseDetected("not_sent_to_ann");
        	
        	exerciseProcessedRepository.insert(processedExercise);
        	exerciseProcessedToExcel.createExcelFile(processedExercise);
        	exerciseProcessedToCSV.createCSVFile(processedExercise.getNormalisedReps(), processedExercise.getId(), processedExercise.getExerciseName());
        	
        	return "Server successfully saved Exercise";
        }
        catch(Exception ex) {
        	return "Server error: " + ex.toString();
        }
    }

}
