package com.gbrosnan.fyp.rest;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gbrosnan.fyp.objects.CSVRequest;
import com.gbrosnan.fyp.objects.CSVRequestList;
import com.gbrosnan.fyp.objects.ExerciseRaw;
import com.gbrosnan.fyp.objects.ProcessedExercise;
import com.gbrosnan.fyp.objects.Rep;
import com.gbrosnan.fyp.objects.SensorSample;
import com.gbrosnan.fyp.persistdata.ExerciseRawRepository;
import com.gbrosnan.fyp.persistdata.MainMongoRepository;
import com.gbrosnan.fyp.preprocess.AdjustArraySize;
import com.gbrosnan.fyp.preprocess.PeakDetect;
import com.gbrosnan.fyp.preprocess.RepCreator;

@RestController
@RequestMapping("/rest/csv")
public class CreateCSV {

	@Autowired
	ExerciseRawRepository exerciseRawRepository;
	
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createCSVFile(@RequestBody CSVRequestList csvRequestList) {
    	
    	//String message = "test";
    	String message = createCSVFileHandler(csvRequestList);
    	return "{\"message\":\"" +  message + "\"}";
    }
    
    private String createCSVFileHandler(CSVRequestList csvRequestList) {
    	
    	List<ExerciseRaw> rawData = compileRawData(csvRequestList);	
    	List<ProcessedExercise> preProcessedData = preProcessData(rawData, csvRequestList);
    	String result = processCSVFile(preProcessedData, csvRequestList.getFileName());
    	return result;
    }
    
    private List<ExerciseRaw> compileRawData(CSVRequestList csvRequestList) {
    	
    	List<ExerciseRaw> exerciseList = new ArrayList<ExerciseRaw>();
    	for(CSVRequest request: csvRequestList.getCsvRequestList()) {    		
    		exerciseList.add(exerciseRawRepository.getExercise(request.getCollectionName(), request.getId()));    		
    	}
    	return exerciseList;
    }
    
    private List<ProcessedExercise> preProcessData(List<ExerciseRaw> rawData, CSVRequestList csvRequestList) {
    	
    	int maximaStart = csvRequestList.getMaximaStart();
    	int maximaRange = csvRequestList.getMaximaRange();
    	int flatPeakRange = csvRequestList.getFlatPeakRange();
    	double flatPeakHeight = csvRequestList.getFlatPeakHeight() / 100;
    	double outerRangeHeight = csvRequestList.getOuterRangeHeight() / 100;
    	  	  	
    	List<ProcessedExercise> processedData = new ArrayList<ProcessedExercise>();
    	for(ExerciseRaw exerciseRaw : rawData) {
    		List<Double> averages = setAverages(exerciseRaw.getSensorSampleList());
    		List<Integer> maximas = PeakDetect.discoverMaximas(averages, maximaStart, maximaRange);
    		maximas = PeakDetect.filterOutFlatPeaks(averages, maximas, flatPeakRange, flatPeakHeight);
    		maximas = PeakDetect.filterOuterRangePeaks(averages, maximas, outerRangeHeight);
    		List<Rep> reps = RepCreator.createRepList(averages, maximas);
    		List<Rep> normalizedReps = RepCreator.normalizeReps(reps);	
     		for(Rep rep : normalizedReps) {	
    			List<Double> adjustedSize = AdjustArraySize.adjustTo(200, rep.getSamples());
    			rep.setSamples(adjustedSize);	
    		}
    		ProcessedExercise processedExercise = new ProcessedExercise(exerciseRaw.getUsername(), exerciseRaw.getExerciseName(), exerciseRaw.getDate(), exerciseRaw.getSensorSampleList(), 
    				averages, reps, normalizedReps, "not_sent_to_ann");
    		processedData.add(processedExercise);
    	}
    	
    	return processedData;
    }
    
	private List<Double> setAverages(List<SensorSample> rawSensorSamples) {
		
		List<Double> averages = new ArrayList<Double>();
		double x, y, z, average;
		for(SensorSample sample : rawSensorSamples) {
			x = sample.getX();
			y = sample.getY();
			z = sample.getZ();
			average = (x + y + z) / 3;
			averages.add(average);	
		}
		return averages;
	}
	

	private String processCSVFile(List<ProcessedExercise> preProcessedData, String filename) {
		
		String result = "CSV process started ";
		
		try {	
			//Path filePath = Paths.get(System.getProperty("catalina.base") + "\\webapps\\fyp-server\\csv");
			File directoryPath = new File("C:\\development\\csv");
			if (!directoryPath.exists()) {
				directoryPath.mkdir();
			}
			File newFilePath = new File(directoryPath + "\\" + filename + ".csv");
			if(newFilePath.exists()) {
				return "Error! Filename already exists";
			}
			FileWriter writer = new FileWriter(directoryPath + "\\" + filename + ".csv");	
			
			int amountOfReps = 0;
			String rowOfValues = "";
			List<Rep> normalizedReps;
			
			for(ProcessedExercise processedExercise : preProcessedData) {
				normalizedReps = processedExercise.getNormalisedReps();
				amountOfReps = normalizedReps.size();
				rowOfValues = "";				
				for(Rep rep : normalizedReps) {
					rowOfValues = "";
					for(double value : rep.getSamples()) {
						rowOfValues += value + ",";
					}
					rowOfValues += getOutputValue(processedExercise.getExerciseName());
					rowOfValues += "\n";
					writer.append(rowOfValues);
					
				}						
			}
			writer.flush();
			writer.close();
			result = "CSV File ceated!";
		}
		catch(Exception ex) {
			result = ex.toString();
		}
		return result;
	}
	
	
	private String getOutputValue(String exerciseName) {
		
		if(exerciseName.equals("bicep_curl")) {
			return "1";
		}
		else if(exerciseName.equals("lat_raise")) {
			return "10";
		} 
		else if(exerciseName.equals("shoulder_shrug")) {
			return "11";
		} 
		else if(exerciseName.equals("back_fly")) {
			return "100";
		} 
		else if(exerciseName.equals("one_arm_row")) {
			return "101";
		} 
		else if(exerciseName.equals("kick_back")) {
			return "110";
		} 
		else if(exerciseName.equals("front_raise")) {
			return "111";
		} 
		else if(exerciseName.equals("seated_tricep")) {
			return "1000";
		} 
		else if(exerciseName.equals("shoulder_press")) {
			return "1001";
		} 
		else {
			return "1010";
		} 
	}
    

}
