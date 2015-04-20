package com.gbrosnan.fyp.rest;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
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
import com.google.gson.Gson;

@RestController
@RequestMapping("/rest/csv")
public class RestCSV {

	@Autowired
	ExerciseRawRepository exerciseRawRepository;
	
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createCSVFile(@RequestBody CSVRequestList csvRequestList) {
    	
    	//String message = "test";
    	String message = createCSVFileHandler(csvRequestList);
    	return "{\"message\":\"" +  message + "\"}";
    }
    
    @RequestMapping(value = "/filenamelist", method = RequestMethod.GET)
    public String createCSVFile() {
    	
    	List<String> fileNameList = new ArrayList<String>();
    	File folder = new File(getFilePath());
    	File[] listOfFiles = folder.listFiles();
    	
    	for(File file : listOfFiles) {
    		if(file.isFile()){    			
    			fileNameList.add(file.getName());
    		}
    	}
    	return new Gson().toJson(fileNameList);
    }
    
    private String getFilePath() {
    	
    	if(System.getProperty("catalina.base").equals("G:\\eclipse_workspaces\\fyp\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0")) {
    		return System.getProperty("catalina.base") + "\\wtpwebapps\\fyp-server\\csv\\";
    	}
    	else if(System.getProperty("catalina.base").equals("/home/gerry/development/fyp-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0")) {
    		return System.getProperty("catalina.base") + "/wtpwebapps/fyp-server/csv/";
    	}
    	else {
    		return System.getProperty("catalina.base") + "/webapps/fyp-server/csv/";
    	}
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
    		
    		
    		List<Rep> reps3Axis = RepCreator.createRepList3Axis(exerciseRaw.getSensorSampleList(),averages,maximas);
    		List<Rep> reps3AxisNormalized = RepCreator.normalizeReps(reps3Axis);
      		for(Rep rep : reps3AxisNormalized) {	
    			List<Double> adjustedSize = AdjustArraySize.adjustTo(600, rep.getSamples());
    			rep.setSamples(adjustedSize);	
    		}  		
    		
    		List<Rep> reps = RepCreator.createRepList(averages, maximas);
    		List<Rep> normalizedReps = RepCreator.normalizeReps(reps);	
     		for(Rep rep : normalizedReps) {	
    			List<Double> adjustedSize = AdjustArraySize.adjustTo(200, rep.getSamples());
    			rep.setSamples(adjustedSize);	
    		}
    		ProcessedExercise processedExercise = new ProcessedExercise(exerciseRaw.getUsername(), exerciseRaw.getExerciseName(), exerciseRaw.getDate(), exerciseRaw.getSensorSampleList(), 
    				averages, maximas, reps, reps3AxisNormalized, "not_sent_to_ann");
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
		
		String result = "CSV process started";
		
		try {	
			File directoryPath = new File(getFilePath());
			if (!directoryPath.exists()) {
				result = "doesn't exist";
				directoryPath.mkdir();
				result = "created";
			}
			
			File newFilePath = new File(directoryPath + "/" + filename + ".csv");
			if(newFilePath.exists()) {
				return "Error! Filename already exists";
			}
			FileWriter writer = new FileWriter(directoryPath + "/" + filename + ".csv");	
			
			int amountOfReps = 0;
			String rowOfValues = "", output = "", digitAsString = "";
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
					output = getOutputValue(processedExercise.getExerciseName());
					for(int digit = 0 ; digit < output.length() ; digit ++ ) {
						digitAsString = Character.toString(output.charAt(digit));
						rowOfValues += digitAsString;
						if(digit < (output.length()-1)) {
							rowOfValues += ",";
						}					
					}
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
			return "000";
		}
		else if(exerciseName.equals("lat_raise")) {
			return "100";
		} 
		else if(exerciseName.equals("kick_back")) {
			return "111";
		} 
		else {
			return "101";
		} 
	}

}
